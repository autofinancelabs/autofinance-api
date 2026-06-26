package com.autofinance.api.creditsimulation.domain.services;

import com.autofinance.api.creditsimulation.domain.model.commands.RequestSimulationCommand;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationId;

/** Application-service port (domain) for the Credit Simulation use cases. */
public interface CreditSimulationCommandService {

    /**
     * Generates a credit simulation: validates the referenced client, takes the sale price/currency from
     * the referenced vehicle offer (ACL), runs the engine and persists it; returns its id.
     */
    SimulationId handle(RequestSimulationCommand command);
}
