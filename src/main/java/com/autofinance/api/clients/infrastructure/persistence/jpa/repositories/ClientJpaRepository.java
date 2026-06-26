package com.autofinance.api.clients.infrastructure.persistence.jpa.repositories;

import com.autofinance.api.clients.domain.model.aggregates.Client;
import com.autofinance.api.clients.domain.model.valueobjects.ClientId;
import com.autofinance.api.clients.domain.repositories.ClientRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA adapter satisfying the {@link ClientRepository} port. {@code save}, {@code findById}
 * and {@code findAll} are inherited from {@link JpaRepository}; {@code existsByDocumentId} is a derived
 * query over the embedded {@code documentId}. All reads/writes are tenant-scoped by Hibernate
 * {@code @TenantId}, so the existence check is per-dealership.
 */
@Repository
public interface ClientJpaRepository
        extends JpaRepository<Client, ClientId>, ClientRepository {
}
