package com.autofinance.api.vehicleoffers.domain.model.queries;

import com.autofinance.api.vehicleoffers.domain.model.valueobjects.VehicleOfferId;

/** Intent to read a single vehicle offer by its id. */
public record GetVehicleOfferByIdQuery(VehicleOfferId vehicleOfferId) {
}
