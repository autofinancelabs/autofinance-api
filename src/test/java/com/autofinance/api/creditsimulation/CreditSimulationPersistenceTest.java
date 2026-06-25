package com.autofinance.api.creditsimulation;

import com.autofinance.api.creditsimulation.domain.model.commands.GenerateSimulationCommand;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationId;
import com.autofinance.api.creditsimulation.domain.repositories.CreditSimulationRepository;
import com.autofinance.api.creditsimulation.domain.services.CreditSimulationCommandService;
import com.autofinance.api.shared.AbstractIntegrationTest;
import com.autofinance.api.shared.infrastructure.multitenancy.TenantContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test (Testcontainers Postgres): the aggregate persists and reloads identically — the
 * jsonb snapshot (schedule incl. nested applied costs, summary) and the child tables (grace, costs) —
 * and data is isolated per dealership tenant.
 */
class CreditSimulationPersistenceTest extends AbstractIntegrationTest {

    @Autowired
    private CreditSimulationCommandService commandService;

    @Autowired
    private CreditSimulationRepository repository;

    @AfterEach
    void clearTenant() {
        TenantContext.clear();
    }

    @Test
    void roundTripsTheJsonbSnapshotAndChildTables() {
        GenerateSimulationCommand command = GoldenDatasets.d1();
        TenantContext.setTenant(command.dealershipId());
        SimulationId id = commandService.handle(command);

        var found = repository.findById(id).orElseThrow();

        assertThat(found.getSchedule()).hasSize(37);                            // schedule jsonb
        assertThat(found.getSchedule().get(0).appliedCosts()).isNotEmpty();     // nested costs survive jsonb
        assertThat(found.getIndicators().tcea()).isNotNull();                   // @Embedded columns
        assertThat(found.getSummary().totalsPerCost()).containsKey("gps");      // summary jsonb (map)
        assertThat(found.getGrace()).hasSize(36);                               // grace_periods child table
        assertThat(found.getCosts()).isNotEmpty();                             // credit_simulation_costs child table
    }

    @Test
    void isolatesSimulationsByDealershipTenant() {
        GenerateSimulationCommand command = GoldenDatasets.d2();
        TenantContext.setTenant(command.dealershipId());
        SimulationId id = commandService.handle(command);

        TenantContext.setTenant(UUID.randomUUID()); // a different dealership
        assertThat(repository.findById(id)).isEmpty();
    }
}
