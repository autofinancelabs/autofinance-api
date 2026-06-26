package com.autofinance.api.vehicleoffers.interfaces.acl;

import java.util.Optional;
import java.util.UUID;

/**
 * Anti-corruption boundary for other contexts (e.g. Credit Simulation) to read a vehicle offer by id.
 * Exposes only the minimal published data; tenant-scoped like the rest of the context.
 */
public interface VehicleOffersContextFacade {

    Optional<VehicleOfferSummary> fetchById(UUID vehicleOfferId);
}
