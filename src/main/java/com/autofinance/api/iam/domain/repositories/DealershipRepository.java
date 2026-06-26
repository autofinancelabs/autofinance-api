package com.autofinance.api.iam.domain.repositories;

import com.autofinance.api.iam.domain.model.aggregates.Dealership;
import com.autofinance.api.shared.domain.model.valueobjects.DealershipId;
import com.autofinance.api.iam.domain.model.valueobjects.Ruc;

import java.util.Optional;

/**
 * Domain port for persisting and retrieving {@link Dealership} aggregates. IAM is the tenant registry, so
 * these are global (NOT tenant-scoped) — {@link #existsByRuc} checks RUC uniqueness across the registry.
 */
public interface DealershipRepository {

    Dealership save(Dealership dealership);

    Optional<Dealership> findById(DealershipId id);

    boolean existsByRuc(Ruc ruc);
}
