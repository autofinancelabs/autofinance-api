package com.autofinance.api.creditsimulation.interfaces.rest.resources;

import com.autofinance.api.creditsimulation.domain.model.valueobjects.Capitalization;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.Currency;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.GraceType;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.RateType;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * dealership, which comes from the {@code X-Dealership-Id} header (the tenant). Enum-typed inputs stay
 * Strings on the wire (mapped to domain enums in the assembler), but are documented with the enum's
 * allowed values via {@code @Schema(implementation = ...)} so the API docs show a dropdown, not "string".
 */
public record GenerateSimulationResource(
        @NotNull UUID clientId,
        @NotNull UUID vehicleOfferId,
        @NotNull @Positive BigDecimal salePrice,
        @NotBlank @Schema(implementation = Currency.class) String currency,
        @NotNull BigDecimal rateValue,
        @NotBlank @Schema(implementation = RateType.class) String rateType,
        @Schema(implementation = Capitalization.class, nullable = true,
                description = "Required only when rateType is NOMINAL") String capitalization,
        @NotNull BigDecimal initialPercentage,
        @NotNull BigDecimal balloonPercentage,
        @Positive int numberOfInstallments,
        @Positive int frequencyDays,
        @Positive int daysPerYear,
        @NotEmpty @ArraySchema(schema = @Schema(implementation = GraceType.class)) List<String> gracePlan,
        @NotNull List<@Valid CostResource> costs,
        @NotNull BigDecimal costOfCapitalAnnual
) {
    public GenerateSimulationResource {
        gracePlan = gracePlan == null ? List.of() : List.copyOf(gracePlan);
        costs = costs == null ? List.of() : List.copyOf(costs);
    }
}
