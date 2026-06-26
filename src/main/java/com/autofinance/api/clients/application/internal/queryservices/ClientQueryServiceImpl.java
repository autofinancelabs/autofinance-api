package com.autofinance.api.clients.application.internal.queryservices;

import com.autofinance.api.clients.domain.model.aggregates.Client;
import com.autofinance.api.clients.domain.model.queries.GetAllClientsQuery;
import com.autofinance.api.clients.domain.model.queries.GetClientByIdQuery;
import com.autofinance.api.clients.domain.repositories.ClientRepository;
import com.autofinance.api.clients.domain.services.ClientQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/** Reads clients through the repository; performs no state changes. */
@Service
public class ClientQueryServiceImpl implements ClientQueryService {

    private final ClientRepository repository;

    public ClientQueryServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> handle(GetClientByIdQuery query) {
        return repository.findById(query.clientId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> handle(GetAllClientsQuery query) {
        return repository.findAll();
    }
}
