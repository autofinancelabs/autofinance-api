package com.autofinance.api.clients.domain.model.aggregates;

import com.autofinance.api.clients.domain.model.events.ClientRegistered;
import com.autofinance.api.clients.domain.model.events.ClientUpdated;
import com.autofinance.api.clients.domain.model.valueobjects.ClientId;
import com.autofinance.api.clients.domain.model.valueobjects.ContactInfo;
import com.autofinance.api.shared.domain.model.valueobjects.DealershipId;
import com.autofinance.api.clients.domain.model.valueobjects.DocumentId;
import com.autofinance.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;
import lombok.Getter;
import org.hibernate.annotations.TenantId;

import java.util.UUID;

/**
 * Aggregate root of the Clients supporting context: the debtor (cliente/deudor) the Credit Simulation
 * core references by-id. The identity document is immutable; only contact data can change. Uniqueness of
 * the document per dealership is enforced at registration (via the repository) and by a DB constraint.
 */
@Getter
@Entity
public class Client extends AuditableAbstractAggregateRoot<Client, ClientId> {

    @EmbeddedId
    private ClientId id;

    @TenantId
    @Column(name = "dealership_id")
    private UUID dealershipId;

    @Embedded
    private DocumentId documentId;

    /** Optional contact data; {@code null} when none was provided. */
    @Embedded
    private ContactInfo contactInfo;

    @Version
    @Column(name = "version")
    private long version;

    protected Client() {
        // for JPA
    }

    public Client(ClientId id, UUID dealershipId, DocumentId documentId, ContactInfo contactInfo) {
        this.id = id;
        this.dealershipId = dealershipId;
        this.documentId = documentId;
        this.contactInfo = contactInfo;
        addDomainEvent(new ClientRegistered(id, new DealershipId(dealershipId)));
    }

    /** Replaces the contact data. The identity document stays immutable. */
    public void updateContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
        addDomainEvent(new ClientUpdated(id, new DealershipId(dealershipId)));
    }

    @Override
    public ClientId getId() {
        return id;
    }
}
