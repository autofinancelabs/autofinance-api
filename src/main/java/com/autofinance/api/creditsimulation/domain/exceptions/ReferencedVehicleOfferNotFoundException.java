package com.autofinance.api.creditsimulation.domain.exceptions;

import com.autofinance.api.shared.domain.exceptions.DomainException;

import java.util.UUID;

/** Raised when a simulation references a vehicle offer that does not exist in the current dealership. */
public class ReferencedVehicleOfferNotFoundException extends DomainException {
    public ReferencedVehicleOfferNotFoundException(UUID vehicleOfferId) {
        super(SimulationErrorCode.VEHICLE_OFFER_NOT_FOUND, "Vehicle offer %s does not exist".formatted(vehicleOfferId));
    }
}
