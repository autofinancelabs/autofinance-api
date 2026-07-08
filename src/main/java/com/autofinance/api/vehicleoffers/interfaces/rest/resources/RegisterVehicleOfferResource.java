package com.autofinance.api.vehicleoffers.interfaces.rest.resources;

import com.autofinance.api.shared.domain.model.valueobjects.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Request body to register a vehicle offer. Mirrors {@code RegisterVehicleOfferCommand} minus the
 * dealership, which comes from the {@code X-Dealership-Id} header (the tenant). {@code currency} stays a
 * String on the wire (mapped to the domain enum in the assembler) but is documented with its allowed
 * values. {@code model3d} is an optional nested object (omitted/null when no 3D model was generated).
 */
public record RegisterVehicleOfferResource(
        @NotBlank String make,
        @NotBlank String model,
        @Positive int year,
        @NotNull @Positive BigDecimal salePrice,
        @NotBlank @Schema(implementation = Currency.class) String currency,
        @Valid Model3dResource model3d
) {
}
