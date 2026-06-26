package com.autofinance.api.clients;

import com.autofinance.api.clients.domain.exceptions.DuplicateClientDocumentException;
import com.autofinance.api.clients.domain.model.commands.RegisterClientCommand;
import com.autofinance.api.clients.domain.model.commands.UpdateClientCommand;
import com.autofinance.api.clients.domain.model.valueobjects.ClientId;
import com.autofinance.api.clients.domain.model.valueobjects.DocumentType;
import com.autofinance.api.clients.domain.repositories.ClientRepository;
import com.autofinance.api.clients.domain.services.ClientCommandService;
import com.autofinance.api.shared.AbstractIntegrationTest;
import com.autofinance.api.shared.infrastructure.multitenancy.TenantContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Integration test (Testcontainers Postgres): a client persists and reloads identically (document +
 * contact), contact data can be updated while the document stays immutable, a duplicate document within
 * the dealership is rejected, and data is isolated per dealership tenant.
 */
class ClientPersistenceTest extends AbstractIntegrationTest {

    @Autowired
    private ClientCommandService commandService;

    @Autowired
    private ClientRepository repository;

    @AfterEach
    void clearTenant() {
        TenantContext.clear();
    }

    private static RegisterClientCommand registerCommand(UUID dealershipId) {
        return new RegisterClientCommand(
                dealershipId, DocumentType.DNI, "12345678",
                "ana@example.com", "+51 999 888 777", "Av. Lima 123");
    }

    @Test
    void roundTripsTheClient() {
        UUID dealershipId = UUID.randomUUID();
        TenantContext.setTenant(dealershipId);
        ClientId id = commandService.handle(registerCommand(dealershipId));

        var found = repository.findById(id).orElseThrow();

        assertThat(found.getDocumentId().type()).isEqualTo(DocumentType.DNI);
        assertThat(found.getDocumentId().number()).isEqualTo("12345678");
        assertThat(found.getContactInfo().email()).isEqualTo("ana@example.com");
        assertThat(found.getContactInfo().phone()).isEqualTo("+51 999 888 777");
        assertThat(found.getContactInfo().address()).isEqualTo("Av. Lima 123");
    }

    @Test
    void updatesContactKeepingTheDocument() {
        UUID dealershipId = UUID.randomUUID();
        TenantContext.setTenant(dealershipId);
        ClientId id = commandService.handle(registerCommand(dealershipId));

        commandService.handle(new UpdateClientCommand(id.value(), "nuevo@example.com", null, null));

        var found = repository.findById(id).orElseThrow();
        assertThat(found.getDocumentId().type()).isEqualTo(DocumentType.DNI);
        assertThat(found.getDocumentId().number()).isEqualTo("12345678");
        assertThat(found.getContactInfo().email()).isEqualTo("nuevo@example.com");
        assertThat(found.getContactInfo().phone()).isNull();
    }

    @Test
    void rejectsADuplicateDocumentInTheDealership() {
        UUID dealershipId = UUID.randomUUID();
        TenantContext.setTenant(dealershipId);
        commandService.handle(registerCommand(dealershipId));

        assertThatThrownBy(() -> commandService.handle(registerCommand(dealershipId)))
                .isInstanceOf(DuplicateClientDocumentException.class);
    }

    @Test
    void isolatesClientsByDealershipTenant() {
        UUID dealershipId = UUID.randomUUID();
        TenantContext.setTenant(dealershipId);
        ClientId id = commandService.handle(registerCommand(dealershipId));

        TenantContext.setTenant(UUID.randomUUID()); // a different dealership
        assertThat(repository.findById(id)).isEmpty();
    }
}
