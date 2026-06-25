package com.autofinance.api.creditsimulation.domain.model.queries;

import com.autofinance.api.creditsimulation.domain.model.valueobjects.ClientId;

/** Intent to read all credit simulations of a client (within the current dealership/tenant). */
public record GetSimulationsByClientIdQuery(ClientId clientId) {
}
