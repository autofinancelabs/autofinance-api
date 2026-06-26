package com.autofinance.api.clients.application.acl;

import com.autofinance.api.clients.domain.model.queries.GetClientByIdQuery;
import com.autofinance.api.clients.domain.model.valueobjects.ClientId;
import com.autofinance.api.clients.domain.services.ClientQueryService;
import com.autofinance.api.clients.interfaces.acl.ClientsContextFacade;
import org.springframework.stereotype.Service;

import java.util.UUID;

/** Implements the published facade by delegating to the query service. */
@Service
public class ClientsContextFacadeImpl implements ClientsContextFacade {

    private final ClientQueryService queryService;

    public ClientsContextFacadeImpl(ClientQueryService queryService) {
        this.queryService = queryService;
    }

    @Override
    public boolean existsById(UUID clientId) {
        return queryService.handle(new GetClientByIdQuery(new ClientId(clientId))).isPresent();
    }
}
