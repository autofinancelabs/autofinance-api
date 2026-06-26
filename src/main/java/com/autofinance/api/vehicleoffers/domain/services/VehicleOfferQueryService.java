package com.autofinance.api.vehicleoffers.domain.services;

import com.autofinance.api.vehicleoffers.domain.model.aggregates.VehicleOffer;
import com.autofinance.api.vehicleoffers.domain.model.queries.GetAllVehicleOffersQuery;
import com.autofinance.api.vehicleoffers.domain.model.queries.GetVehicleOfferByIdQuery;

import java.util.List;
import java.util.Optional;

/** Application-service port (domain) for the Vehicle Offers read use cases. */
public interface VehicleOfferQueryService {

    Optional<VehicleOffer> handle(GetVehicleOfferByIdQuery query);

    List<VehicleOffer> handle(GetAllVehicleOffersQuery query);
}
