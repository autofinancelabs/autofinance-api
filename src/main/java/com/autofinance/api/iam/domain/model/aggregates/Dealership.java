package com.autofinance.api.iam.domain.model.aggregates;

import com.autofinance.api.iam.domain.model.events.DealershipRegistered;
import com.autofinance.api.iam.domain.model.valueobjects.DealershipId;
import com.autofinance.api.iam.domain.model.valueobjects.Ruc;
import com.autofinance.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;
import lombok.Getter;

/**
 * Aggregate root of the IAM context's account/tenant: a dealership (concesionaria). It is the tenant
 * registry itself, so it is NOT {@code @TenantId}-scoped. RUC is the unique business identifier.
 */
@Getter
@Entity
public class Dealership extends AuditableAbstractAggregateRoot<Dealership, DealershipId> {

    @EmbeddedId
    private DealershipId id;

    @Column(name = "name")
    private String name;

    @Embedded
    private Ruc ruc;

    /** Optional account contact email (distinct from the login email on {@code User}). */
    @Column(name = "contact_email")
    private String contactEmail;

    @Version
    @Column(name = "version")
    private long version;

    protected Dealership() {
        // for JPA
    }

    public Dealership(DealershipId id, String name, Ruc ruc, String contactEmail) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Dealership name cannot be null or blank");
        }
        this.id = id;
        this.name = name;
        this.ruc = ruc;
        this.contactEmail = contactEmail;
        addDomainEvent(new DealershipRegistered(id));
    }

    @Override
    public DealershipId getId() {
        return id;
    }
}
