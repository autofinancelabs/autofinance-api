package com.autofinance.api.creditsimulation.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

/**
 * A configurable cost (request and response). {@code value} is an amount (FIXED) or a rate
 * (ON_BALANCE/ON_SALE_PRICE); {@code basis} ∈ FIXED/ON_BALANCE/ON_SALE_PRICE, {@code timing} ∈
 * INITIAL/PERIODIC.
 */
public record CostResource(
        @NotBlank String name,
        @NotNull @PositiveOrZero BigDecimal value,
        @NotBlank String basis,
        @NotBlank String timing,
        boolean embedded
) {
}
