package com.autofinance.api.creditsimulation.domain.services;

import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceConfiguration;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceType;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.PeriodicCosts;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.ScheduleRow;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Term;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Stateless domain service that builds the payment schedule: French method + Compra Inteligente
 * (deferred balloon), grace (S/T/P) with cuota recomputation after grace, periodic costs, and the
 * final balloon settlement row. Implements docs/report/algoritmo.md.
 * <p>
 * When credit-life insurance is embedded, both the regular installment and the deferred balloon
 * accrue it: the rate used is {@code j = i + TSD}. The balloon's present value is therefore
 * {@code balloon / (1 + j)^(n+1)} — it grows for the n ordinary periods plus the settlement period.
 */
public final class ScheduleCalculator {

    private static final BigDecimal ZERO = BigDecimal.ZERO;

    /**
     * Present value of the deferred balloon, discounted at the balloon's growth rate over {@code n+1}
     * periods (the n ordinary periods plus the settlement period). Shared with the aggregate so the
     * financed balance and the schedule agree.
     */
    public static BigDecimal balloonPresentValue(BigDecimal balloon,
                                                 BigDecimal periodicRate,
                                                 BigDecimal creditLifeInsuranceRate,
                                                 boolean creditLifeInsuranceEmbedded,
                                                 int numberOfInstallments) {
        if (balloon.signum() <= 0) {
            return ZERO;
        }
        BigDecimal balloonRate = creditLifeInsuranceEmbedded
                ? periodicRate.add(creditLifeInsuranceRate, FinancialMath.MC)
                : periodicRate;
        return balloon.divide(
                FinancialMath.pow(BigDecimal.ONE.add(balloonRate), numberOfInstallments + 1),
                FinancialMath.MC);
    }

    public List<ScheduleRow> build(BigDecimal loanAmount,
                                   BigDecimal balloon,
                                   BigDecimal periodicRate,
                                   Term term,
                                   GraceConfiguration grace,
                                   PeriodicCosts costs,
                                   boolean creditLifeInsuranceEmbedded) {
        final int n = term.numberOfInstallments();
        final BigDecimal i = periodicRate;
        final BigDecimal tsd = costs.creditLifeInsuranceRate();
        final BigDecimal rateForInstallment = creditLifeInsuranceEmbedded ? i.add(tsd, FinancialMath.MC) : i;
        final BigDecimal fixed = costs.fixedPerPeriod();
        final boolean hasBalloon = balloon.signum() > 0;

        BigDecimal balloonBalance = balloonPresentValue(balloon, i, tsd, creditLifeInsuranceEmbedded, n);
        BigDecimal regularBalance = loanAmount.subtract(balloonBalance, FinancialMath.MC);
        BigDecimal installment = null; // computed lazily at the first ordinary period

        List<ScheduleRow> rows = new ArrayList<>(n + 1);

        for (int t = 1; t <= n; t++) {
            GraceType graceType = grace.at(t);
            BalloonStep balloonStep = growBalloon(balloonBalance, i, tsd, hasBalloon, creditLifeInsuranceEmbedded);
            balloonBalance = balloonStep.closing();

            BigDecimal openingRegular = regularBalance;
            BigDecimal interestDisplayed = openingRegular.multiply(i, FinancialMath.MC); // plain i
            BigDecimal creditLifeInsurance = openingRegular.multiply(tsd, FinancialMath.MC);

            BigDecimal periodInstallment;
            BigDecimal amortization;
            BigDecimal closingRegular;
            BigDecimal cashFlow;

            switch (graceType) {
                case TOTAL -> {
                    periodInstallment = ZERO;
                    amortization = ZERO;
                    closingRegular = openingRegular.add(interestDisplayed, FinancialMath.MC); // capitalizes
                    cashFlow = creditLifeInsurance.add(fixed, FinancialMath.MC);
                }
                case PARTIAL -> {
                    periodInstallment = interestDisplayed; // pays interest only
                    amortization = ZERO;
                    closingRegular = openingRegular;
                    cashFlow = periodInstallment.add(creditLifeInsurance, FinancialMath.MC).add(fixed, FinancialMath.MC);
                }
                case NONE -> {
                    if (installment == null) {
                        int remaining = remainingOrdinaryPeriods(grace, t, n);
                        BigDecimal factor = BigDecimal.ONE.subtract(
                                FinancialMath.pow(BigDecimal.ONE.add(rateForInstallment), -remaining), FinancialMath.MC);
                        installment = openingRegular.multiply(rateForInstallment, FinancialMath.MC)
                                .divide(factor, FinancialMath.MC);
                    }
                    periodInstallment = installment;
                    BigDecimal interestForAmort = openingRegular.multiply(rateForInstallment, FinancialMath.MC);
                    amortization = periodInstallment.subtract(interestForAmort, FinancialMath.MC);
                    closingRegular = openingRegular.subtract(amortization, FinancialMath.MC);
                    cashFlow = periodInstallment.add(fixed, FinancialMath.MC);
                    if (!creditLifeInsuranceEmbedded) {
                        cashFlow = cashFlow.add(creditLifeInsurance, FinancialMath.MC);
                    }
                }
                default -> throw new IllegalStateException("Unknown grace type: " + graceType);
            }

            regularBalance = closingRegular;

            rows.add(new ScheduleRow(
                    t, graceType,
                    balloonStep.opening(), balloonStep.interest(), balloonStep.insurance(), balloonStep.closing(),
                    openingRegular, interestDisplayed, periodInstallment, amortization,
                    creditLifeInsurance, costs.allRiskInsurance(), costs.gps(), costs.shippingFees(), costs.adminFees(),
                    closingRegular, cashFlow));
        }

        if (hasBalloon) {
            // Settlement: the balloon grows one final period (its display closes at the nominal value),
            // and is paid at its nominal amount plus the fixed period costs.
            BalloonStep settlement = growBalloon(balloonBalance, i, tsd, true, creditLifeInsuranceEmbedded);
            BigDecimal settlementCashFlow = balloon.add(fixed, FinancialMath.MC);
            rows.add(new ScheduleRow(
                    n + 1, GraceType.NONE,
                    settlement.opening(), settlement.interest(), settlement.insurance(), ZERO,
                    ZERO, ZERO, ZERO, ZERO,
                    ZERO, costs.allRiskInsurance(), costs.gps(), costs.shippingFees(), costs.adminFees(),
                    ZERO, settlementCashFlow));
        }

        return rows;
    }

    private BalloonStep growBalloon(BigDecimal opening, BigDecimal i, BigDecimal tsd,
                                    boolean hasBalloon, boolean embedded) {
        if (!hasBalloon) {
            return new BalloonStep(opening, ZERO, ZERO, opening);
        }
        BigDecimal interest = opening.multiply(i, FinancialMath.MC);
        BigDecimal insurance = embedded ? opening.multiply(tsd, FinancialMath.MC) : ZERO;
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
}
