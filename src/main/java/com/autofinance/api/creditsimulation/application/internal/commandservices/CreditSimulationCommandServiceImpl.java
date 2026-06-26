package com.autofinance.api.creditsimulation.application.internal.commandservices;

import com.autofinance.api.clients.interfaces.acl.ClientsContextFacade;
import com.autofinance.api.creditsimulation.domain.exceptions.ReferencedClientNotFoundException;
import com.autofinance.api.creditsimulation.domain.exceptions.ReferencedVehicleOfferNotFoundException;
import com.autofinance.api.creditsimulation.domain.model.aggregates.CreditSimulation;
import com.autofinance.api.creditsimulation.domain.model.aggregates.CreditSimulationFactory;
import com.autofinance.api.creditsimulation.domain.model.commands.GenerateSimulationCommand;
import com.autofinance.api.creditsimulation.domain.model.commands.RequestSimulationCommand;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationId;
import com.autofinance.api.creditsimulation.domain.repositories.CreditSimulationRepository;
import com.autofinance.api.creditsimulation.domain.services.CreditSimulationCommandService;
import com.autofinance.api.shared.domain.model.valueobjects.Currency;
import com.autofinance.api.vehicleoffers.interfaces.acl.VehicleOfferSummary;
import com.autofinance.api.vehicleoffers.interfaces.acl.VehicleOffersContextFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Orchestrates the "generate simulation" use case. Anti-corruption layer: the referenced client must
 * exist, and the sale price/currency are taken from the referenced vehicle offer (not the request) — both
 * via the supporting contexts' published facades, tenant-scoped. The factory + engine then run as before.
 */
@Service
public class CreditSimulationCommandServiceImpl implements CreditSimulationCommandService {

    private final CreditSimulationRepository repository;
    private final ClientsContextFacade clientsFacade;
    private final VehicleOffersContextFacade vehicleOffersFacade;
    private final CreditSimulationFactory factory = new CreditSimulationFactory();

    public CreditSimulationCommandServiceImpl(CreditSimulationRepository repository,
                                              ClientsContextFacade clientsFacade,
                                              VehicleOffersContextFacade vehicleOffersFacade) {
        this.repository = repository;
        this.clientsFacade = clientsFacade;
        this.vehicleOffersFacade = vehicleOffersFacade;
    }

    @Override
    @Transactional
    public SimulationId handle(RequestSimulationCommand request) {
        if (!clientsFacade.existsById(request.clientId())) {
            throw new ReferencedClientNotFoundException(request.clientId());
        }
        VehicleOfferSummary offer = vehicleOffersFacade.fetchById(request.vehicleOfferId())
                .orElseThrow(() -> new ReferencedVehicleOfferNotFoundException(request.vehicleOfferId()));

        GenerateSimulationCommand command = new GenerateSimulationCommand(
                request.dealershipId(), request.clientId(), request.vehicleOfferId(),
                offer.salePrice(), Currency.valueOf(offer.currency()),
                request.rateValue(), request.rateType(), request.capitalization(),
                request.initialPercentage(), request.balloonPercentage(),
                request.numberOfInstallments(), request.frequencyDays(), request.daysPerYear(),
                request.gracePlan(), request.costs(), request.costOfCapitalAnnual());

        CreditSimulation simulation = factory.create(command);
        repository.save(simulation);
        return simulation.getId();
    }
}
