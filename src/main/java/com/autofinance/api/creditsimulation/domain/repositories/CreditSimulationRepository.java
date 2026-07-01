package com.autofinance.api.creditsimulation.domain.repositories;

import com.autofinance.api.creditsimulation.domain.model.aggregates.CreditSimulation;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.ClientId;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationId;

import java.util.List;
import java.util.Optional;

/**
 * Domain port for persisting and retrieving {@link CreditSimulation} aggregates. Queries are
 * auto-filtered by the current dealership (tenant) via Hibernate {@code @TenantId}.
 */
public interface CreditSimulationRepository {

    CreditSimulation save(CreditSimulation simulation);

    Optional<CreditSimulation> findById(SimulationId id);

    List<CreditSimulation> findByClientId(ClientId clientId);

    /** All simulations of the current dealership (tenant-filtered via {@code @TenantId}). */
    List<CreditSimulation> findAll();
}
