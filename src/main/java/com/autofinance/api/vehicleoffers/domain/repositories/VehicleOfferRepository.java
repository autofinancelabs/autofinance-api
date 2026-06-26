package com.autofinance.api.vehicleoffers.domain.repositories;

import com.autofinance.api.vehicleoffers.domain.model.aggregates.VehicleOffer;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.VehicleOfferId;

import java.util.List;
import java.util.Optional;

/**
 * Domain port for persisting and retrieving {@link VehicleOffer} aggregates. Queries are
 * auto-filtered by the current dealership (tenant) via Hibernate {@code @TenantId}.
 */
public interface VehicleOfferRepository {

    VehicleOffer save(VehicleOffer offer);

    Optional<VehicleOffer> findById(VehicleOfferId id);

    List<VehicleOffer> findAll();
}
