package com.autofinance.api.creditsimulation.interfaces.rest.resources;

import com.autofinance.api.creditsimulation.domain.model.valueobjects.RateType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * A rate: its value, type (NOMINAL/EFFECTIVE), capitalization in days (null for EFFECTIVE) and the
 * rate period in days (the period the value is quoted over; null = annual).
 */
public record RateResource(
        BigDecimal value,
        @Schema(implementation = RateType.class) String type,
        @Schema(type = "integer", nullable = true) Integer capitalization,
        @Schema(type = "integer", nullable = true) Integer ratePeriod
) {
}
