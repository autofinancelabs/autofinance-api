package com.autofinance.api.creditsimulation.domain.services;

import com.autofinance.api.creditsimulation.domain.model.aggregates.CreditSimulation;
import com.autofinance.api.creditsimulation.domain.model.queries.GetSimulationByIdQuery;
import com.autofinance.api.creditsimulation.domain.model.queries.GetSimulationsByClientIdQuery;

import java.util.List;
import java.util.Optional;

/** Application-service port (domain) for the Credit Simulation read use cases. */
public interface CreditSimulationQueryService {

    Optional<CreditSimulation> handle(GetSimulationByIdQuery query);

    List<CreditSimulation> handle(GetSimulationsByClientIdQuery query);
}
