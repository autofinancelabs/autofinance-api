package com.autofinance.api.creditsimulation;

import com.autofinance.api.clients.domain.model.commands.RegisterClientCommand;
import com.autofinance.api.clients.domain.model.valueobjects.DocumentType;
import com.autofinance.api.clients.domain.services.ClientCommandService;
import com.autofinance.api.creditsimulation.domain.exceptions.ReferencedClientNotFoundException;
import com.autofinance.api.creditsimulation.domain.exceptions.ReferencedVehicleOfferNotFoundException;
import com.autofinance.api.creditsimulation.domain.model.commands.GenerateSimulationCommand;
import com.autofinance.api.creditsimulation.domain.model.commands.RequestSimulationCommand;
import com.autofinance.api.creditsimulation.domain.model.valueobjects.SimulationId;
import com.autofinance.api.creditsimulation.domain.repositories.CreditSimulationRepository;
import com.autofinance.api.creditsimulation.domain.services.CreditSimulationCommandService;
import com.autofinance.api.shared.AbstractIntegrationTest;
import com.autofinance.api.shared.infrastructure.multitenancy.TenantContext;
import com.autofinance.api.vehicleoffers.domain.model.commands.RegisterVehicleOfferCommand;
import com.autofinance.api.vehicleoffers.domain.services.VehicleOfferCommandService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration test (Testcontainers Postgres): the aggregate persists and reloads identically — the jsonb
 * snapshot and the child tables — the ACL takes the sale price from a real vehicle offer and validates the
 * client, and data is isolated per dealership tenant.
 */
class CreditSimulationPersistenceTest extends AbstractIntegrationTest {

    @Autowired
    private CreditSimulationCommandService commandService;

    @Autowired
    private CreditSimulationRepository repository;

    @Autowired
    private ClientCommandService clientCommandService;

    @Autowired
    private VehicleOfferCommandService vehicleOfferCommandService;

    @AfterEach
    void clearTenant() {
        TenantContext.clear();
    }

    private UUID registerOffer(UUID dealershipId, GenerateSimulationCommand d) {
        return vehicleOfferCommandService.handle(new RegisterVehicleOfferCommand(
                dealershipId, "Toyota", "Corolla", 2024, d.salePrice(), d.currency())).value();
    }

    private UUID registerClient(UUID dealershipId, String document) {
        return clientCommandService.handle(new RegisterClientCommand(
                dealershipId, DocumentType.DNI, document, "Cliente", "De Prueba", null, null, null)).value();
    }

    private static RequestSimulationCommand requestFrom(GenerateSimulationCommand d, UUID dealershipId,
                                                        UUID clientId, UUID vehicleOfferId) {
        return new RequestSimulationCommand(
                dealershipId, clientId, vehicleOfferId,
                d.rateValue(), d.rateType(), d.capitalization(), d.ratePeriod(),
                d.initialPercentage(), d.balloonPercentage(),
                d.numberOfInstallments(), d.frequencyDays(), d.daysPerYear(),
                d.gracePlan(), d.costs(), d.costOfCapitalAnnual());
    }

    @Test
    void roundTripsTheJsonbSnapshotAndChildTables() {
        UUID dealershipId = UUID.randomUUID();
        TenantContext.setTenant(dealershipId);
        GenerateSimulationCommand d = GoldenDatasets.d1();
        UUID offerId = registerOffer(dealershipId, d);
        UUID clientId = registerClient(dealershipId, "12345678");

        SimulationId id = commandService.handle(requestFrom(d, dealershipId, clientId, offerId));

        var found = repository.findById(id).orElseThrow();
        assertThat(found.getSchedule()).hasSize(37);                         // price came from the offer
        assertThat(found.getSchedule().get(0).appliedCosts()).isNotEmpty();
        assertThat(found.getIndicators().tcea()).isNotNull();
        assertThat(found.getSummary().totalsPerCost()).containsKey("gps");
        assertThat(found.getGrace()).hasSize(36);
        assertThat(found.getCosts()).isNotEmpty();
    }

    @Test
    void isolatesSimulationsByDealershipTenant() {
        UUID dealershipId = UUID.randomUUID();
        TenantContext.setTenant(dealershipId);
        GenerateSimulationCommand d = GoldenDatasets.d2();
        UUID offerId = registerOffer(dealershipId, d);
        UUID clientId = registerClient(dealershipId, "87654321");

        SimulationId id = commandService.handle(requestFrom(d, dealershipId, clientId, offerId));

        TenantContext.setTenant(UUID.randomUUID()); // a different dealership
        assertThat(repository.findById(id)).isEmpty();
    }

    @Test
    void rejectsAMissingClient() {
        UUID dealershipId = UUID.randomUUID();
        TenantContext.setTenant(dealershipId);
        GenerateSimulationCommand d = GoldenDatasets.d2();
        UUID offerId = registerOffer(dealershipId, d);

        assertThatThrownBy(() -> commandService.handle(
                requestFrom(d, dealershipId, UUID.randomUUID(), offerId)))
                .isInstanceOf(ReferencedClientNotFoundException.class);
    }

    @Test
    void rejectsAMissingVehicleOffer() {
        UUID dealershipId = UUID.randomUUID();
        TenantContext.setTenant(dealershipId);
        GenerateSimulationCommand d = GoldenDatasets.d2();
        UUID clientId = registerClient(dealershipId, "11223344");

        assertThatThrownBy(() -> commandService.handle(
                requestFrom(d, dealershipId, clientId, UUID.randomUUID())))
                .isInstanceOf(ReferencedVehicleOfferNotFoundException.class);
    }
}
