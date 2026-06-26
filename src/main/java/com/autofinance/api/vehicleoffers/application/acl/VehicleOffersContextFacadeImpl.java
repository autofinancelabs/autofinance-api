package com.autofinance.api.vehicleoffers.application.acl;

import com.autofinance.api.vehicleoffers.domain.model.queries.GetVehicleOfferByIdQuery;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.VehicleOfferId;
import com.autofinance.api.vehicleoffers.domain.services.VehicleOfferQueryService;
import com.autofinance.api.vehicleoffers.interfaces.acl.VehicleOfferSummary;
import com.autofinance.api.vehicleoffers.interfaces.acl.VehicleOffersContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/** Implements the published facade by delegating to the query service and mapping to primitives. */
@Service
public class VehicleOffersContextFacadeImpl implements VehicleOffersContextFacade {

    private final VehicleOfferQueryService queryService;

    public VehicleOffersContextFacadeImpl(VehicleOfferQueryService queryService) {
        this.queryService = queryService;
    }

    @Override
    public Optional<VehicleOfferSummary> fetchById(UUID vehicleOfferId) {
        return queryService.handle(new GetVehicleOfferByIdQuery(new VehicleOfferId(vehicleOfferId)))
                .map(offer -> new VehicleOfferSummary(
                        offer.getSalePrice().amount(),
                        offer.getSalePrice().currency().name()));
    }
}
