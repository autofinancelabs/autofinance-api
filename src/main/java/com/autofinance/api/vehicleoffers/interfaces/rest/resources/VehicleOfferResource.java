package com.autofinance.api.vehicleoffers.interfaces.rest.resources;

import java.util.UUID;

/** Response view of a vehicle offer (the stored snapshot). Plan fields are null when no plan applies. */
public record VehicleOfferResource(
        UUID id,
        String make,
        String model,
        int year,
        MoneyResource salePrice,
        String planName,
        Integer planInstallments
) {
}
