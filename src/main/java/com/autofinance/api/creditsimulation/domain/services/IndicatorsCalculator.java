package com.autofinance.api.creditsimulation.domain.services;

import com.autofinance.api.creditsimulation.domain.exceptions.IrrNotBracketedException;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Indicators;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.ScheduleRow;

import java.math.BigDecimal;
import java.util.List;

/**
 * Stateless domain service computing VAN/TIR/TCEA from the debtor's perspective over the schedule
 * cash flows. The loan is the inflow at t=0; the period cash flows are outflows from t=1.
 */
public final class IndicatorsCalculator {

    private static final BigDecimal ONE = BigDecimal.ONE;
    private static final BigDecimal TOLERANCE = new BigDecimal("1e-10");
    private static final int MAX_ITERATIONS = 100;

    public Indicators compute(BigDecimal loanAmount,
                              List<ScheduleRow> schedule,
                              BigDecimal periodicCostOfCapital,
                              int installmentsPerYear,
                              BigDecimal effectiveAnnualRate,
                              BigDecimal periodicRate) {
        List<BigDecimal> flows = schedule.stream().map(ScheduleRow::cashFlow).toList();

        BigDecimal npv = netPresentValue(loanAmount, flows, periodicCostOfCapital);
        BigDecimal periodicIrr = internalRateOfReturn(loanAmount, flows);
        BigDecimal tcea = FinancialMath.pow(ONE.add(periodicIrr), installmentsPerYear).subtract(ONE);

        return new Indicators(npv, periodicIrr, tcea, effectiveAnnualRate, periodicRate, periodicCostOfCapital);
    }

    /** {@code NPV = loanAmount - Σ flow_t / (1 + rate)^t}. */
    private BigDecimal netPresentValue(BigDecimal loanAmount, List<BigDecimal> flows, BigDecimal rate) {
        BigDecimal npv = loanAmount;
        BigDecimal onePlus = ONE.add(rate);
        for (int t = 1; t <= flows.size(); t++) {
            BigDecimal discounted = flows.get(t - 1).divide(FinancialMath.pow(onePlus, t), FinancialMath.MC);
            npv = npv.subtract(discounted, FinancialMath.MC);
        }
        return npv;
    }

    /** Period IRR: the rate making NPV = 0, via bisection on [0, 1]. */
    private BigDecimal internalRateOfReturn(BigDecimal loanAmount, List<BigDecimal> flows) {
        BigDecimal a = BigDecimal.ZERO;
        BigDecimal b = ONE;
        BigDecimal fa = netPresentValue(loanAmount, flows, a);
        BigDecimal fb = netPresentValue(loanAmount, flows, b);
        if (fa.signum() == fb.signum()) {
            throw new IrrNotBracketedException();
        }
        BigDecimal mid = a;
        for (int iteration = 0; iteration < MAX_ITERATIONS; iteration++) {
            mid = a.add(b, FinancialMath.MC).divide(BigDecimal.valueOf(2), FinancialMath.MC);
            BigDecimal fm = netPresentValue(loanAmount, flows, mid);
            if (fm.abs().compareTo(TOLERANCE) < 0) {
                return mid;
            }
            if (fm.signum() == fa.signum()) {
                a = mid;
                fa = fm;
            } else {
                b = mid;
            }
        }
        return mid;
    }
}
