package com.autofinance.api.vehicleoffers.domain.exceptions;

/** Raised when a vehicle offer violates a business invariant (e.g. a non-positive sale price). */
public class InvalidVehicleOfferException extends RuntimeException {
    public InvalidVehicleOfferException(String message) {
        super(message);
    }
}
