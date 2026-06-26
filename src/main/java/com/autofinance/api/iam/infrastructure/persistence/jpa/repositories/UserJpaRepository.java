package com.autofinance.api.iam.infrastructure.persistence.jpa.repositories;

import com.autofinance.api.iam.domain.model.aggregates.User;
import com.autofinance.api.iam.domain.model.valueobjects.UserId;
import com.autofinance.api.iam.domain.repositories.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA adapter satisfying the {@link UserRepository} port. {@code save}/{@code findById} are
 * inherited; {@code findByEmail}/{@code existsByEmail} are derived over the embedded {@code email} and
 * {@code findByUsername}/{@code existsByUsername} over the {@code username} column. Global (no
 * {@code @TenantId} filtering — email and username are unique across the whole registry).
 */
@Repository
public interface UserJpaRepository
        extends JpaRepository<User, UserId>, UserRepository {
}
