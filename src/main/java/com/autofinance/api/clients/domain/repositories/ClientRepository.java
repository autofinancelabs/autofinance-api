package com.autofinance.api.clients.domain.repositories;

import com.autofinance.api.clients.domain.model.aggregates.Client;
import com.autofinance.api.clients.domain.model.valueobjects.ClientId;
import com.autofinance.api.clients.domain.model.valueobjects.DocumentId;

import java.util.List;
import java.util.Optional;

/**
 * Domain port for persisting and retrieving {@link Client} aggregates. Queries are auto-filtered by the
 * current dealership (tenant) via Hibernate {@code @TenantId}, so {@link #existsByDocumentId} checks the
 * document uniqueness within the current dealership.
 */
public interface ClientRepository {

    Client save(Client client);

    Optional<Client> findById(ClientId id);

    List<Client> findAll();

    boolean existsByDocumentId(DocumentId documentId);
}
