package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Accumulated results of a simulation (the "Resultados acumulados" of the source examples):
 * totals over the schedule, including the total amount to pay and the totals per cost.
 */
public record SimulationSummary(
        BigDecimal totalInterest,
        BigDecimal totalAmortization,
        BigDecimal totalLoanInstallments,
        BigDecimal totalToPay,
        Map<String, BigDecimal> totalsPerCost
) {
    public SimulationSummary {
        totalsPerCost = Map.copyOf(totalsPerCost);
    }

    public SimulationSummary() {
        this(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, Map.of());
    }
}
