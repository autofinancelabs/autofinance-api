package com.autofinance.api.creditsimulation.interfaces.rest.resources;

import com.autofinance.api.creditsimulation.domain.model.valueobjects.Capitalization;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.RateType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/** A rate: its value, type (NOMINAL/EFFECTIVE) and capitalization (null for EFFECTIVE). */
public record RateResource(
        BigDecimal value,
        @Schema(implementation = RateType.class) String type,
        @Schema(implementation = Capitalization.class, nullable = true) String capitalization
) {
}
