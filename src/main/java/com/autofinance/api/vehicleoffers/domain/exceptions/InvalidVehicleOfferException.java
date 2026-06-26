package com.autofinance.api.vehicleoffers.domain.exceptions;

import com.autofinance.api.shared.domain.exceptions.DomainException;

/** Raised when a vehicle offer violates a business invariant (e.g. a non-positive sale price). */
public class InvalidVehicleOfferException extends DomainException {
    public InvalidVehicleOfferException(String message) {
        super(VehicleOfferErrorCode.INVALID_VEHICLE_OFFER, message);
    }
}
