package com.autofinance.api.clients.domain.model.queries;

import com.autofinance.api.clients.domain.model.valueobjects.ClientId;

/** Intent to read a single client by its id. */
public record GetClientByIdQuery(ClientId clientId) {
}
