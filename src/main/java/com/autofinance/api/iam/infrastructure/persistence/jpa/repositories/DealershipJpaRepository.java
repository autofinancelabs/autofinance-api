package com.autofinance.api.iam.infrastructure.persistence.jpa.repositories;

import com.autofinance.api.iam.domain.model.aggregates.Dealership;
import com.autofinance.api.shared.domain.model.valueobjects.DealershipId;
import com.autofinance.api.iam.domain.repositories.DealershipRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA adapter satisfying the {@link DealershipRepository} port. {@code save}/{@code findById}
 * are inherited; {@code existsByRuc} is a derived query over the embedded {@code ruc}. Global (IAM is the
 * tenant registry — no {@code @TenantId} filtering).
 */
@Repository
public interface DealershipJpaRepository
        extends JpaRepository<Dealership, DealershipId>, DealershipRepository {
}
