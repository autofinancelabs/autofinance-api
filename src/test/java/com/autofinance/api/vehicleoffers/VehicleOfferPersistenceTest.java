package com.autofinance.api.vehicleoffers;

import com.autofinance.api.shared.AbstractIntegrationTest;
import com.autofinance.api.shared.domain.model.valueobjects.Currency;
import com.autofinance.api.shared.infrastructure.multitenancy.TenantContext;
import com.autofinance.api.vehicleoffers.domain.model.commands.RegisterVehicleOfferCommand;
import com.autofinance.api.vehicleoffers.domain.model.commands.UpdateVehicleOfferCommand;
import com.autofinance.api.vehicleoffers.domain.model.queries.GetAllVehicleOffersQuery;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.VehicleOfferId;
import com.autofinance.api.vehicleoffers.domain.repositories.VehicleOfferRepository;
import com.autofinance.api.vehicleoffers.domain.services.VehicleOfferCommandService;
import com.autofinance.api.vehicleoffers.domain.services.VehicleOfferQueryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test (Testcontainers Postgres): a vehicle offer persists and reloads identically — the
 * {@code @Embedded} vehicle, the sale-price {@link com.autofinance.api.shared.domain.model.valueobjects.Money}
 * and the optional plan — survives an update, and data is isolated per dealership tenant.
 */
class VehicleOfferPersistenceTest extends AbstractIntegrationTest {

    @Autowired
    private VehicleOfferCommandService commandService;

    @Autowired
    private VehicleOfferQueryService queryService;

    @Autowired
    private VehicleOfferRepository repository;

    @AfterEach
    void clearTenant() {
        TenantContext.clear();
    }

    private static RegisterVehicleOfferCommand registerCommand(UUID dealershipId) {
        return new RegisterVehicleOfferCommand(
                dealershipId, "Toyota", "Corolla", 2024,
                new BigDecimal("50000.00"), Currency.PEN, "Plan 36", 36);
    }

    @Test
    void roundTripsTheOffer() {
        UUID dealershipId = UUID.randomUUID();
        TenantContext.setTenant(dealershipId);
        VehicleOfferId id = commandService.handle(registerCommand(dealershipId));

        var found = repository.findById(id).orElseThrow();

        assertThat(found.getVehicle().make()).isEqualTo("Toyota");
        assertThat(found.getVehicle().model()).isEqualTo("Corolla");
        assertThat(found.getVehicle().year()).isEqualTo(2024);
        assertThat(found.getSalePrice().amount()).isEqualByComparingTo("50000.00");
        assertThat(found.getSalePrice().currency()).isEqualTo(Currency.PEN);
        assertThat(found.getPlan().name()).isEqualTo("Plan 36");
        assertThat(found.getPlan().installments()).isEqualTo(36);
        assertThat(queryService.handle(new GetAllVehicleOffersQuery())).hasSize(1);
    }

    @Test
    void reflectsAnUpdate() {
        UUID dealershipId = UUID.randomUUID();
        TenantContext.setTenant(dealershipId);
        VehicleOfferId id = commandService.handle(registerCommand(dealershipId));

        commandService.handle(new UpdateVehicleOfferCommand(
                id.value(), "Toyota", "Yaris", 2025,
                new BigDecimal("42000.00"), Currency.USD, null, null));

        var found = repository.findById(id).orElseThrow();
        assertThat(found.getVehicle().model()).isEqualTo("Yaris");
        assertThat(found.getVehicle().year()).isEqualTo(2025);
        assertThat(found.getSalePrice().amount()).isEqualByComparingTo("42000.00");
        assertThat(found.getSalePrice().currency()).isEqualTo(Currency.USD);
        assertThat(found.getPlan()).isNull();
    }

    @Test
    void isolatesOffersByDealershipTenant() {
        UUID dealershipId = UUID.randomUUID();
        TenantContext.setTenant(dealershipId);
        VehicleOfferId id = commandService.handle(registerCommand(dealershipId));

        TenantContext.setTenant(UUID.randomUUID()); // a different dealership
        assertThat(repository.findById(id)).isEmpty();
    }
}
