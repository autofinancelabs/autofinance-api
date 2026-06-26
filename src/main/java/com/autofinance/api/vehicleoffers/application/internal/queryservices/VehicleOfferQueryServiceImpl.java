package com.autofinance.api.vehicleoffers.application.internal.queryservices;

import com.autofinance.api.vehicleoffers.domain.model.aggregates.VehicleOffer;
import com.autofinance.api.vehicleoffers.domain.model.queries.GetAllVehicleOffersQuery;
import com.autofinance.api.vehicleoffers.domain.model.queries.GetVehicleOfferByIdQuery;
import com.autofinance.api.vehicleoffers.domain.repositories.VehicleOfferRepository;
import com.autofinance.api.vehicleoffers.domain.services.VehicleOfferQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/** Reads vehicle offers through the repository; performs no state changes. */
@Service
public class VehicleOfferQueryServiceImpl implements VehicleOfferQueryService {

    private final VehicleOfferRepository repository;

    public VehicleOfferQueryServiceImpl(VehicleOfferRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<VehicleOffer> handle(GetVehicleOfferByIdQuery query) {
        return repository.findById(query.vehicleOfferId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehicleOffer> handle(GetAllVehicleOffersQuery query) {
        return repository.findAll();
    }
}
