package com.autofinance.api.iam.domain.repositories;

import com.autofinance.api.iam.domain.model.aggregates.User;
import com.autofinance.api.iam.domain.model.valueobjects.UserId;
import com.autofinance.api.shared.domain.model.valueobjects.Email;

import java.util.Optional;

/**
 * Domain port for persisting and retrieving {@link User} aggregates. Global (NOT tenant-scoped): email and
 * username are unique across the whole registry. {@code findBy*} support the global login lookup (security
 * pass); {@code existsBy*} support the registration uniqueness guards.
 */
public interface UserRepository {

    User save(User user);

    Optional<User> findById(UserId id);

    Optional<User> findByEmail(Email email);

    Optional<User> findByUsername(String username);

    boolean existsByEmail(Email email);

    boolean existsByUsername(String username);
}
