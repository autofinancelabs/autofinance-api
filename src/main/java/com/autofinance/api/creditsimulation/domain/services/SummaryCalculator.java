package com.autofinance.api.creditsimulation.domain.services;

import com.autofinance.api.creditsimulation.domain.model.valueobjects.AppliedCost;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.ScheduleRow;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationSummary;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Stateless domain service that aggregates a schedule into the accumulated totals. */
public final class SummaryCalculator {

    public SimulationSummary compute(List<ScheduleRow> schedule) {
        BigDecimal totalInterest = BigDecimal.ZERO;
        BigDecimal totalAmortization = BigDecimal.ZERO;
        BigDecimal totalLoanInstallments = BigDecimal.ZERO;
        BigDecimal totalToPay = BigDecimal.ZERO;
        Map<String, BigDecimal> totalsPerCost = new LinkedHashMap<>();

        for (ScheduleRow row : schedule) {
            totalInterest = totalInterest.add(row.interest(), FinancialMath.MC);
            totalAmortization = totalAmortization.add(row.amortization(), FinancialMath.MC);
            totalLoanInstallments = totalLoanInstallments.add(row.installment(), FinancialMath.MC);
            totalToPay = totalToPay.add(row.cashFlow(), FinancialMath.MC);
            for (AppliedCost cost : row.appliedCosts()) {
                totalsPerCost.merge(cost.name(), cost.amount(),
                        (a, b) -> a.add(b, FinancialMath.MC));
            }
        }

        return new SimulationSummary(totalInterest, totalAmortization, totalLoanInstallments,
                totalToPay, totalsPerCost);
    }
}
