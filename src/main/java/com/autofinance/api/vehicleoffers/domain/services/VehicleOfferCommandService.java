package com.autofinance.api.vehicleoffers.domain.services;

import com.autofinance.api.vehicleoffers.domain.model.commands.RegisterVehicleOfferCommand;
import com.autofinance.api.vehicleoffers.domain.model.commands.UpdateVehicleOfferCommand;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.VehicleOfferId;

import java.util.Optional;

/** Application-service port (domain) for the Vehicle Offers write use cases. */
public interface VehicleOfferCommandService {

    /** Registers a vehicle offer from the command and persists it; returns its id. */
    VehicleOfferId handle(RegisterVehicleOfferCommand command);

    /** Updates an existing vehicle offer; returns its id, or empty if the offer was not found. */
    Optional<VehicleOfferId> handle(UpdateVehicleOfferCommand command);
}
