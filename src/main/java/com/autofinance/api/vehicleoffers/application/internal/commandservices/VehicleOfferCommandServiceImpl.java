package com.autofinance.api.vehicleoffers.application.internal.commandservices;

import com.autofinance.api.shared.domain.model.valueobjects.Money;
import com.autofinance.api.vehicleoffers.domain.model.aggregates.VehicleOffer;
import com.autofinance.api.vehicleoffers.domain.model.aggregates.VehicleOfferFactory;
import com.autofinance.api.vehicleoffers.domain.model.commands.RegisterVehicleOfferCommand;
import com.autofinance.api.vehicleoffers.domain.model.commands.UpdateVehicleOfferCommand;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.Vehicle;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.VehicleOfferId;
import com.autofinance.api.vehicleoffers.domain.repositories.VehicleOfferRepository;
import com.autofinance.api.vehicleoffers.domain.services.VehicleOfferCommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Orchestrates the vehicle-offer write use cases: builds the aggregate via the domain factory (register)
 * or re-applies inputs to an existing one (update), and persists it. Domain events registered by the
 * aggregate are published when it is saved. The tenant (dealership) comes from the current request
 * context (Hibernate {@code @TenantId} fills the column and scopes the lookup).
 */
@Service
public class VehicleOfferCommandServiceImpl implements VehicleOfferCommandService {

    private final VehicleOfferRepository repository;
    private final VehicleOfferFactory factory = new VehicleOfferFactory();

    public VehicleOfferCommandServiceImpl(VehicleOfferRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public VehicleOfferId handle(RegisterVehicleOfferCommand command) {
        VehicleOffer offer = factory.create(command);
        repository.save(offer);
        return offer.getId();
    }

    @Override
    @Transactional
    public Optional<VehicleOfferId> handle(UpdateVehicleOfferCommand command) {
        return repository.findById(new VehicleOfferId(command.vehicleOfferId()))
                .map(offer -> {
                    offer.update(
                            new Vehicle(command.make(), command.model(), command.year()),
                            new Money(command.salePrice(), command.currency()));
                    repository.save(offer);
                    return offer.getId();
                });
    }
}
