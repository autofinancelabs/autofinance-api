package com.autofinance.api.creditsimulation.infrastructure.persistence.jpa.repositories;

import com.autofinance.api.creditsimulation.domain.model.aggregates.CreditSimulation;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationId;
import com.autofinance.api.creditsimulation.domain.repositories.CreditSimulationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA adapter satisfying the {@link CreditSimulationRepository} port. {@code save} and
 * {@code findById} are inherited from {@link JpaRepository}; {@code findByClientId} is a derived query
 * over the embedded {@code clientId}. All reads/writes are tenant-scoped by Hibernate {@code @TenantId}.
 */
@Repository
public interface CreditSimulationJpaRepository
        extends JpaRepository<CreditSimulation, SimulationId>, CreditSimulationRepository {
}
