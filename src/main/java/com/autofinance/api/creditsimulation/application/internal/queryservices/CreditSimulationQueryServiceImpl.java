package com.autofinance.api.creditsimulation.application.internal.queryservices;

import com.autofinance.api.creditsimulation.domain.model.aggregates.CreditSimulation;
import com.autofinance.api.creditsimulation.domain.model.queries.GetSimulationByIdQuery;
import com.autofinance.api.creditsimulation.domain.model.queries.GetSimulationsByClientIdQuery;
import com.autofinance.api.creditsimulation.domain.repositories.CreditSimulationRepository;
import com.autofinance.api.creditsimulation.domain.services.CreditSimulationQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/** Reads credit simulations through the repository; performs no state changes. */
@Service
public class CreditSimulationQueryServiceImpl implements CreditSimulationQueryService {

    private final CreditSimulationRepository repository;

    public CreditSimulationQueryServiceImpl(CreditSimulationRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CreditSimulation> handle(GetSimulationByIdQuery query) {
        return repository.findById(query.simulationId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<CreditSimulation> handle(GetSimulationsByClientIdQuery query) {
        return repository.findByClientId(query.clientId());
    }
}
