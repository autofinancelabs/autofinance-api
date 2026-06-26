package com.autofinance.api.clients.domain.services;

import com.autofinance.api.clients.domain.model.aggregates.Client;
import com.autofinance.api.clients.domain.model.queries.GetAllClientsQuery;
import com.autofinance.api.clients.domain.model.queries.GetClientByIdQuery;

import java.util.List;
import java.util.Optional;

/** Application-service port (domain) for the Clients read use cases. */
public interface ClientQueryService {

    Optional<Client> handle(GetClientByIdQuery query);

    List<Client> handle(GetAllClientsQuery query);
}
