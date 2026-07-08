package com.autofinance.api.vehicleoffers.interfaces.rest.resources;

import com.autofinance.api.shared.domain.model.valueobjects.Currency;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 * Request body to update a vehicle offer. Same shape as {@code RegisterVehicleOfferResource}; the offer
 * id comes from the path. {@code currency} stays a String on the wire. {@code model3d} is optional
 * (null to clear the 3D model).
 */
public record UpdateVehicleOfferResource(
        @NotBlank String make,
        @NotBlank String model,
        @Positive int year,
        @NotNull @Positive BigDecimal salePrice,
        @NotBlank @Schema(implementation = Currency.class) String currency,
        @Valid Model3dResource model3d
) {
}
