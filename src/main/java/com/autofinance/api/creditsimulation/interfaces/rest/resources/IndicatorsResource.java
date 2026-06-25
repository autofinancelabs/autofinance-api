package com.autofinance.api.creditsimulation.interfaces.rest.resources;

import java.math.BigDecimal;

/** Financial indicators of the simulation. */
public record IndicatorsResource(
        BigDecimal npv,
        BigDecimal periodicIrr,
        BigDecimal tcea,
        BigDecimal effectiveAnnualRate,
        BigDecimal periodicRate,
        BigDecimal periodicCostOfCapital
) {
}
