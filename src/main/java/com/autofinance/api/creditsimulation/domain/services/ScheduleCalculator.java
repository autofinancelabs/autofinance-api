package com.autofinance.api.creditsimulation.domain.services;

import com.autofinance.api.creditsimulation.domain.model.valueobjects.AppliedCost;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Cost;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Costs;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceConfiguration;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceType;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.ScheduleRow;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Term;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Stateless domain service that builds the payment schedule: French method + Compra Inteligente
 * (deferred balloon), grace (S/T/P) with cuota recomputation after grace, flexible periodic costs,
 * and the final balloon settlement row. Implements docs/report/algoritmo.md (the formulas come from
 * docs/guides/).
 * <p>
 * Embedded costs (credit-life insurance) raise the installment rate to {@code j = i + Σ rate} and
 * capitalize into the balloon ({@code VP = balloon/(1+j)^(n+1)}); on ordinary rows they live inside
 * the installment, on grace rows they are charged separately.
 */
public final class ScheduleCalculator {

    private static final BigDecimal ZERO = BigDecimal.ZERO;

    /** Present value of the deferred balloon, discounted at its growth rate over {@code n+1} periods. */
    public static BigDecimal balloonPresentValue(BigDecimal balloon, BigDecimal balloonRate, int numberOfInstallments) {
        if (balloon.signum() <= 0) {
            return ZERO;
        }
        return balloon.divide(
                FinancialMath.pow(BigDecimal.ONE.add(balloonRate), numberOfInstallments + 1),
                FinancialMath.MC);
    }

    public List<ScheduleRow> build(BigDecimal loanAmount,
                                   BigDecimal balloon,
                                   BigDecimal periodicRate,
                                   BigDecimal salePrice,
                                   Term term,
                                   GraceConfiguration grace,
                                   Costs costs) {
        final int n = term.numberOfInstallments();
        final BigDecimal i = periodicRate;
        final BigDecimal embeddedRate = costs.embeddedRate();
        final BigDecimal rateForInstallment = i.add(embeddedRate, FinancialMath.MC);
        final List<Cost> periodic = costs.periodic();
        final boolean hasBalloon = balloon.signum() > 0;

        BigDecimal balloonBalance = balloonPresentValue(balloon, rateForInstallment, n);
        BigDecimal regularBalance = loanAmount.subtract(balloonBalance, FinancialMath.MC);
        BigDecimal installment = null; // computed lazily at the first ordinary period

        List<ScheduleRow> rows = new ArrayList<>(n + 1);

        for (int t = 1; t <= n; t++) {
            GraceType graceType = grace.at(t);
            BalloonStep balloonStep = growBalloon(balloonBalance, i, embeddedRate, hasBalloon);
            balloonBalance = balloonStep.closing();

            BigDecimal openingRegular = regularBalance;
            BigDecimal interestDisplayed = openingRegular.multiply(i, FinancialMath.MC); // plain i
            CostEval ce = evaluateCosts(periodic, costs, openingRegular, salePrice);

            BigDecimal periodInstallment;
            BigDecimal amortization;
            BigDecimal closingRegular;
            BigDecimal cashFlow;

            switch (graceType) {
                case TOTAL -> {
                    periodInstallment = ZERO;
                    amortization = ZERO;
                    closingRegular = openingRegular.add(interestDisplayed, FinancialMath.MC); // capitalizes
                    cashFlow = ce.embedded().add(ce.other(), FinancialMath.MC);
                }
                case PARTIAL -> {
                    periodInstallment = interestDisplayed; // pays interest only
                    amortization = ZERO;
                    closingRegular = openingRegular;
                    cashFlow = periodInstallment.add(ce.embedded(), FinancialMath.MC).add(ce.other(), FinancialMath.MC);
                }
                case NONE -> {
                    if (installment == null) {
                        int remaining = remainingOrdinaryPeriods(grace, t, n);
                        if (rateForInstallment.signum() == 0) {
                            // 0% rate: equal principal payments (avoids division by zero in the annuity).
                            installment = openingRegular.divide(BigDecimal.valueOf(remaining), FinancialMath.MC);
                        } else {
                            BigDecimal factor = BigDecimal.ONE.subtract(
                                    FinancialMath.pow(BigDecimal.ONE.add(rateForInstallment), -remaining), FinancialMath.MC);
                            installment = openingRegular.multiply(rateForInstallment, FinancialMath.MC)
                                    .divide(factor, FinancialMath.MC);
                        }
                    }
                    periodInstallment = installment;
                    BigDecimal interestForAmort = openingRegular.multiply(rateForInstallment, FinancialMath.MC);
                    amortization = periodInstallment.subtract(interestForAmort, FinancialMath.MC);
                    closingRegular = openingRegular.subtract(amortization, FinancialMath.MC);
                    cashFlow = periodInstallment.add(ce.other(), FinancialMath.MC); // embedded already inside installment
                }
                default -> throw new IllegalStateException("Unknown grace type: " + graceType);
            }

            regularBalance = closingRegular;

            rows.add(new ScheduleRow(
                    t, graceType,
                    balloonStep.opening(), balloonStep.interest(), balloonStep.insurance(), balloonStep.closing(),
                    openingRegular, interestDisplayed, periodInstallment, amortization,
                    closingRegular, cashFlow, ce.applied()));
        }

        if (hasBalloon) {
            // Settlement: the balloon grows one final period; it is paid at its nominal amount plus
            // the period costs (evaluated at a zero regular balance: balance-based costs vanish).
            BalloonStep settlement = growBalloon(balloonBalance, i, embeddedRate, true);
            CostEval ce = evaluateCosts(periodic, costs, ZERO, salePrice);
            BigDecimal settlementCashFlow = balloon.add(ce.other(), FinancialMath.MC);
            rows.add(new ScheduleRow(
                    n + 1, GraceType.NONE,
                    settlement.opening(), settlement.interest(), settlement.insurance(), ZERO,
                    ZERO, ZERO, ZERO, ZERO,
                    ZERO, settlementCashFlow, ce.applied()));
        }

        return rows;
    }

    private CostEval evaluateCosts(List<Cost> periodic, Costs costs,
                                   BigDecimal openingBalance, BigDecimal salePrice) {
        BigDecimal embedded = ZERO;
        BigDecimal other = ZERO;
        List<AppliedCost> applied = new ArrayList<>();
        for (Cost c : periodic) {
            BigDecimal amount = costs.amountFor(c, openingBalance, salePrice);
            // every applied cost is recorded for display/totals; whether it hits the cash flow is
            // decided per grace type (embedded costs are inside the installment on ordinary rows).
            applied.add(new AppliedCost(c.name(), amount));
            if (c.embedded()) {
                embedded = embedded.add(amount, FinancialMath.MC);
            } else {
                other = other.add(amount, FinancialMath.MC);
            }
        }
        return new CostEval(embedded, other, applied);
    }

    private BalloonStep growBalloon(BigDecimal opening, BigDecimal i, BigDecimal embeddedRate, boolean hasBalloon) {
        if (!hasBalloon) {
            return new BalloonStep(opening, ZERO, ZERO, opening);
        }
        BigDecimal interest = opening.multiply(i, FinancialMath.MC);
        BigDecimal insurance = opening.multiply(embeddedRate, FinancialMath.MC);
        BigDecimal closing = opening.add(interest, FinancialMath.MC).add(insurance, FinancialMath.MC);
        return new BalloonStep(opening, interest, insurance, closing);
    }

    /** Number of remaining ordinary (NONE) periods from {@code t} to {@code n}, inclusive. */
    private int remainingOrdinaryPeriods(GraceConfiguration grace, int t, int n) {
        int count = 0;
        for (int p = t; p <= n; p++) {
            if (grace.at(p) == GraceType.NONE) {
                count++;
            }
        }
        return count;
    }

    private record BalloonStep(BigDecimal opening, BigDecimal interest, BigDecimal insurance, BigDecimal closing) {
    }

    private record CostEval(BigDecimal embedded, BigDecimal other, List<AppliedCost> applied) {
    }
}
