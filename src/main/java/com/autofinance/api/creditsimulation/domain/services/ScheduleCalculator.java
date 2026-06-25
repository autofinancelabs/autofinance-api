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
 * (cuotón), grace (S/T/P) with cuota recomputation after grace, periodic costs, and the final
 * balloon settlement row. Implements docs/report/algoritmo.md.
 * <p>
 * The {@code cuotón} block (its balance columns) is informational; the contractual balloon is
 * settled at its nominal value in the final row.
 */
public final class ScheduleCalculator {

    private static final BigDecimal ONE = BigDecimal.ONE;
    private static final BigDecimal ZERO = BigDecimal.ZERO;

    public List<ScheduleRow> build(BigDecimal loanAmount,
                                   BigDecimal cuoton,
                                   BigDecimal periodicRate,
                                   Term term,
                                   GraceConfiguration grace,
                                   PeriodicCosts costs,
                                   boolean desgravamenEmbebido) {
        final int n = term.numberOfInstallments();
        final BigDecimal i = periodicRate;
        final BigDecimal tsd = costs.creditLifeInsuranceRate();
        final BigDecimal rateForInstallment = desgravamenEmbebido ? i.add(tsd, FinancialMath.MC) : i;
        final BigDecimal fixed = costs.fixedPerPeriod();
        final boolean hasBalloon = cuoton.signum() > 0;

        BigDecimal presentValueOfBalloon = hasBalloon
                ? cuoton.divide(FinancialMath.pow(ONE.add(i), n), FinancialMath.MC)
                : ZERO;

        BigDecimal regularBalance = loanAmount.subtract(presentValueOfBalloon, FinancialMath.MC);
        BigDecimal balloonBalance = presentValueOfBalloon;
        BigDecimal installment = null; // computed lazily at the first ordinary period

        List<ScheduleRow> rows = new ArrayList<>(n + 1);

        for (int t = 1; t <= n; t++) {
            GraceType graceType = grace.at(t);

            // Balloon block — grows with i each period (informational).
            BigDecimal openingBalloon = balloonBalance;
            BigDecimal interestBalloon = hasBalloon ? openingBalloon.multiply(i, FinancialMath.MC) : ZERO;
            BigDecimal closingBalloon = openingBalloon.add(interestBalloon, FinancialMath.MC);
            balloonBalance = closingBalloon;

            // Regular installment block.
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
                        BigDecimal factor = ONE.subtract(
                                FinancialMath.pow(ONE.add(rateForInstallment), -remaining), FinancialMath.MC);
                        installment = openingRegular.multiply(rateForInstallment, FinancialMath.MC)
                                .divide(factor, FinancialMath.MC);
                    }
                    periodInstallment = installment;
                    BigDecimal interestForAmort = openingRegular.multiply(rateForInstallment, FinancialMath.MC);
                    amortization = periodInstallment.subtract(interestForAmort, FinancialMath.MC);
                    closingRegular = openingRegular.subtract(amortization, FinancialMath.MC);
                    cashFlow = periodInstallment.add(fixed, FinancialMath.MC);
                    if (!desgravamenEmbebido) {
                        cashFlow = cashFlow.add(creditLifeInsurance, FinancialMath.MC);
                    }
                }
                default -> throw new IllegalStateException("Unknown grace type: " + graceType);
            }

            regularBalance = closingRegular;

            rows.add(new ScheduleRow(
                    t, graceType,
                    openingBalloon, interestBalloon, closingBalloon,
                    openingRegular, interestDisplayed, periodInstallment, amortization,
                    creditLifeInsurance, costs.allRiskInsurance(), costs.gps(), costs.shippingFees(), costs.adminFees(),
                    closingRegular, cashFlow));
        }

        if (hasBalloon) {
            BigDecimal settlementCashFlow = cuoton.add(fixed, FinancialMath.MC);
            rows.add(new ScheduleRow(
                    n + 1, GraceType.NONE,
                    balloonBalance, ZERO, ZERO,
                    ZERO, ZERO, ZERO, ZERO,
                    ZERO, costs.allRiskInsurance(), costs.gps(), costs.shippingFees(), costs.adminFees(),
                    ZERO, settlementCashFlow));
        }

        return rows;
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
}
