package com.autofinance.api.clients.application.internal.commandservices;

import com.autofinance.api.clients.domain.exceptions.DuplicateClientDocumentException;
import com.autofinance.api.clients.domain.model.aggregates.Client;
import com.autofinance.api.clients.domain.model.aggregates.ClientFactory;
import com.autofinance.api.clients.domain.model.commands.RegisterClientCommand;
import com.autofinance.api.clients.domain.model.commands.UpdateClientCommand;
import com.autofinance.api.clients.domain.model.valueobjects.ClientId;
import com.autofinance.api.clients.domain.model.valueobjects.ContactInfo;
import com.autofinance.api.clients.domain.model.valueobjects.DocumentId;
import com.autofinance.api.clients.domain.repositories.ClientRepository;
import com.autofinance.api.clients.domain.services.ClientCommandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Orchestrates the client write use cases: registers a new client (rejecting a duplicate identity
 * document within the dealership) or updates an existing client's contact data, and persists it. Domain
 * events registered by the aggregate are published when it is saved. The tenant (dealership) comes from
 * the current request context (Hibernate {@code @TenantId} scopes the uniqueness check and the lookup).
 */
@Service
public class ClientCommandServiceImpl implements ClientCommandService {

    private final ClientRepository repository;
    private final ClientFactory factory = new ClientFactory();

    public ClientCommandServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public ClientId handle(RegisterClientCommand command) {
        DocumentId documentId = new DocumentId(command.documentType(), command.documentNumber());
        if (repository.existsByDocumentId(documentId)) {
            throw new DuplicateClientDocumentException(documentId);
        }
        Client client = factory.create(command);
        repository.save(client);
        return client.getId();
    }

    @Override
    @Transactional
    public Optional<ClientId> handle(UpdateClientCommand command) {
        return repository.findById(new ClientId(command.clientId()))
                .map(client -> {
                    client.updateContactInfo(
                            ContactInfo.of(command.email(), command.phone(), command.address()));
                    repository.save(client);
                    return client.getId();
                });
    }
}
