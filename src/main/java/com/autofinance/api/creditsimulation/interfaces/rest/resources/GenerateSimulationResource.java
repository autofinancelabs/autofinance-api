package com.autofinance.api.creditsimulation.interfaces.rest.resources;

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
 * Request body to generate a credit simulation. The dealership (tenant) comes from the authenticated user
 * and the sale price/currency are taken from the referenced vehicle offer (ACL) — so the body carries
 * neither. Enum-typed inputs stay Strings on the wire (mapped in the assembler), documented with the
 * enum's allowed values via {@code @Schema(implementation = ...)}.
 */
public record GenerateSimulationResource(
        @NotNull UUID clientId,
        @NotNull UUID vehicleOfferId,
        @NotNull BigDecimal rateValue,
        @NotBlank @Schema(implementation = RateType.class) String rateType,
        @Schema(type = "integer", nullable = true,
                description = "Capitalization (compounding) frequency in days for NOMINAL rates: required "
                        + "(e.g. 1=daily, 30=monthly, 90=quarterly, 180=semiannual, 360=annual, or any value "
                        + "like 100). Omit for EFFECTIVE rates.")
                Integer capitalization,
        @Schema(type = "integer", nullable = true,
                description = "Period in days the rate value is quoted over (both types). Optional; omit = "
                        + "annual (e.g. TNA / TEA). For NOMINAL it is independent of the capitalization.")
                Integer ratePeriod,
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
