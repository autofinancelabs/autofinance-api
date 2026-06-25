package com.autofinance.api.creditsimulation.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.Map;

/** Accumulated totals of the simulation. */
public record SummaryResource(
        BigDecimal totalInterest,
        BigDecimal totalAmortization,
        BigDecimal totalLoanInstallments,
        BigDecimal totalToPay,
        Map<String, BigDecimal> totalsPerCost
) {
}
