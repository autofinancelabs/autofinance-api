package com.autofinance.api.vehicleoffers.interfaces.rest.resources;

import java.util.UUID;

/**
 * Response view of a vehicle offer (the stored snapshot). {@code model3d} is null when the offer has no
 * 3D model.
 */
public record VehicleOfferResource(
        UUID id,
        String make,
        String model,
        int year,
        MoneyResource salePrice,
        Model3dResource model3d
) {
}
