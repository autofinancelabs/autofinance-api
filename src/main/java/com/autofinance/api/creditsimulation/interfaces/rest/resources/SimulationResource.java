package com.autofinance.api.creditsimulation.interfaces.rest.resources;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/** Full response view of a generated credit simulation (the stored snapshot). */
public record SimulationResource(
        UUID id,
        UUID clientId,
        UUID vehicleOfferId,
        MoneyResource salePrice,
        RateResource rate,
        BigDecimal initialPercentage,
        BigDecimal balloonPercentage,
        TermResource term,
        List<String> grace,
        List<CostResource> costs,
        RateResource costOfCapital,
        MoneyResource loanAmount,
        MoneyResource financedBalance,
        IndicatorsResource indicators,
        List<ScheduleRowResource> schedule,
        SummaryResource summary,
        String state
) {
}
