package com.autofinance.api.creditsimulation.application.internal.commandservices;

import com.autofinance.api.creditsimulation.domain.model.aggregates.CreditSimulation;
import com.autofinance.api.creditsimulation.domain.model.aggregates.CreditSimulationFactory;
import com.autofinance.api.creditsimulation.domain.model.commands.GenerateSimulationCommand;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationId;
import com.autofinance.api.creditsimulation.domain.repositories.CreditSimulationRepository;
import com.autofinance.api.creditsimulation.domain.services.CreditSimulationCommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Orchestrates the "generate simulation" use case: builds the aggregate via the domain factory and
 * persists it. Domain events registered by the aggregate are published when it is saved. The tenant
 * (dealership) comes from the current request context (Hibernate {@code @TenantId} fills the column).
 */
@Service
public class CreditSimulationCommandServiceImpl implements CreditSimulationCommandService {

    private final CreditSimulationRepository repository;
    private final CreditSimulationFactory factory = new CreditSimulationFactory();

    public CreditSimulationCommandServiceImpl(CreditSimulationRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public SimulationId handle(GenerateSimulationCommand command) {
        CreditSimulation simulation = factory.create(command);
        repository.save(simulation);
        return simulation.getId();
    }
}
