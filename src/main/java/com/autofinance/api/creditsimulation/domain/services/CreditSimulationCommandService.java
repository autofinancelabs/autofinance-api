package com.autofinance.api.creditsimulation.domain.services;

import com.autofinance.api.creditsimulation.domain.model.commands.RequestSimulationCommand;
import com.autofinance.api.creditsimulation.domain.model.commands.UpdateSimulationCommand;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationId;

import java.util.Optional;

/** Application-service port (domain) for the Credit Simulation use cases. */
public interface CreditSimulationCommandService {

    /**
     * Generates a credit simulation: validates the referenced client, takes the sale price/currency from
     * the referenced vehicle offer (ACL), runs the engine and persists it; returns its id.
     */
    SimulationId handle(RequestSimulationCommand command);

    /**
     * Edits an existing simulation: validates the referenced client and vehicle offer (ACL), reconfigures
     * the aggregate with the new inputs, regenerates the schedule/indicators and persists it. Returns the
     * id, or empty if no simulation with that id exists in the current dealership.
     */
    Optional<SimulationId> handle(UpdateSimulationCommand command);
}
