package com.autofinance.api.creditsimulation.domain.services;

import com.autofinance.api.creditsimulation.domain.model.commands.GenerateSimulationCommand;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationId;

/** Application-service port (domain) for the Credit Simulation use cases. */
public interface CreditSimulationCommandService {

    /** Generates a credit simulation from the command and persists it; returns its id. */
    SimulationId handle(GenerateSimulationCommand command);
}
