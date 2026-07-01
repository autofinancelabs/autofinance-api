package com.autofinance.api.creditsimulation.interfaces.rest.resources;

import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceType;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationState;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;

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
        @ArraySchema(schema = @Schema(implementation = GraceType.class)) List<String> grace,
        List<CostResource> costs,
        RateResource costOfCapital,
        MoneyResource loanAmount,
        MoneyResource financedBalance,
        IndicatorsResource indicators,
        List<ScheduleRowResource> schedule,
        SummaryResource summary,
        @Schema(implementation = SimulationState.class) String state,
        @Schema(type = "string", format = "date-time", nullable = true,
                description = "Creation timestamp (ISO-8601), or null if not yet persisted.")
        String createdAt
) {
}
