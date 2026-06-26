package com.autofinance.api.clients.domain.services;

import com.autofinance.api.clients.domain.model.commands.RegisterClientCommand;
import com.autofinance.api.clients.domain.model.commands.UpdateClientCommand;
import com.autofinance.api.clients.domain.model.valueobjects.ClientId;

import java.util.Optional;

/** Application-service port (domain) for the Clients write use cases. */
public interface ClientCommandService {

    /** Registers a client from the command and persists it; returns its id. */
    ClientId handle(RegisterClientCommand command);

    /** Updates a client's contact data; returns its id, or empty if the client was not found. */
    Optional<ClientId> handle(UpdateClientCommand command);
}
