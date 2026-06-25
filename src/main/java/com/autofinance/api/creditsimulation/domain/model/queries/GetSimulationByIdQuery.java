package com.autofinance.api.creditsimulation.domain.model.queries;

import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationId;

/** Intent to read a single credit simulation by its id. */
public record GetSimulationByIdQuery(SimulationId simulationId) {
}
