package com.autofinance.api.creditsimulation.interfaces.rest.resources;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Request body to generate a credit simulation. Mirrors {@code GenerateSimulationCommand} minus the
 * dealership, which comes from the {@code X-Dealership-Id} header (the tenant). Enum-typed inputs are
 * Strings (mapped to domain enums in the assembler), so the public contract carries no domain types.
 */
public record GenerateSimulationResource(
        @NotNull UUID clientId,
        @NotNull UUID vehicleOfferId,
        @NotNull @Positive BigDecimal salePrice,
        @NotBlank String currency,
        @NotNull BigDecimal rateValue,
        @NotBlank String rateType,
        String capitalization,
        @NotNull BigDecimal initialPercentage,
        @NotNull BigDecimal balloonPercentage,
        @Positive int numberOfInstallments,
        @Positive int frequencyDays,
        @Positive int daysPerYear,
        @NotEmpty List<String> gracePlan,
        @NotNull List<@Valid CostResource> costs,
        @NotNull BigDecimal costOfCapitalAnnual
) {
    public GenerateSimulationResource {
        gracePlan = gracePlan == null ? List.of() : List.copyOf(gracePlan);
        costs = costs == null ? List.of() : List.copyOf(costs);
    }
}
