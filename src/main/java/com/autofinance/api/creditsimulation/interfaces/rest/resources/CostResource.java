package com.autofinance.api.creditsimulation.interfaces.rest.resources;

import com.autofinance.api.creditsimulation.domain.model.valueobjects.CostBasis;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.CostTiming;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

/**
 * A configurable cost (request and response). {@code value} is an amount (FIXED) or a rate
 * (ON_BALANCE/ON_SALE_PRICE). {@code basis} and {@code timing} stay Strings on the wire but are
 * documented with their enum's allowed values.
 */
public record CostResource(
        @NotBlank String name,
        @NotNull @PositiveOrZero BigDecimal value,
        @NotBlank @Schema(implementation = CostBasis.class) String basis,
        @NotBlank @Schema(implementation = CostTiming.class) String timing,
        boolean embedded
) {
}
