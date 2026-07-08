package com.autofinance.api.vehicleoffers.interfaces.rest.resources;

import com.autofinance.api.vehicleoffers.domain.model.valueobjects.Model3dPreset;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

/**
 * Wire shape of a vehicle offer's optional 3D model. Present as a nested object on the request/response
 * only when the offer has a 3D model. {@code preset} stays a String on the wire (mapped to the domain
 * enum in the assembler); colors are 6-digit hex strings; the option flags default to false when null.
 */
public record Model3dResource(
        @Schema(implementation = Model3dPreset.class) String preset,
        @Pattern(regexp = "^#[0-9a-fA-F]{6}$", message = "bodyColor must be a #RRGGBB hex color") String bodyColor,
        @Pattern(regexp = "^#[0-9a-fA-F]{6}$", message = "windowColor must be a #RRGGBB hex color") String windowColor,
        Boolean sportWheels,
        Boolean spoiler,
        Boolean panoRoof,
        String plateText
) {
}
