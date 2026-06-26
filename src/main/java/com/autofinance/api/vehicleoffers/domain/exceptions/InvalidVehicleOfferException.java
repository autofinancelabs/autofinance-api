package com.autofinance.api.vehicleoffers.domain.exceptions;

/**
 * Raised when a vehicle offer violates a business invariant (e.g. a non-positive sale price).
 * <p>
 * Extends {@link IllegalArgumentException} so the current (shared-for-now) REST advice maps it to a
 * 400 {@code VALIDATION_FAILED}. Next iteration this re-parents onto the shared {@code DomainException}
 * base once the error-handling refactor lands.
 */
public class InvalidVehicleOfferException extends IllegalArgumentException {
    public InvalidVehicleOfferException(String message) {
        super(message);
    }
}
