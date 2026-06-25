package com.autofinance.api.creditsimulation.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;

/**
 * Profitability/transparency indicators from the debtor's perspective, plus a rate echo.
 * {@code periodicIrr} is the period IRR; {@code tcea} its annualization.
 */
@Embeddable
public record Indicators(
        @Column(name = "npv") BigDecimal npv,
        @Column(name = "periodic_irr") BigDecimal periodicIrr,
        @Column(name = "tcea") BigDecimal tcea,
        @Column(name = "effective_annual_rate") BigDecimal effectiveAnnualRate,
        @Column(name = "periodic_rate") BigDecimal periodicRate,
        @Column(name = "periodic_cost_of_capital") BigDecimal periodicCostOfCapital
) {
    public Indicators() {
        this(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO,
                BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
