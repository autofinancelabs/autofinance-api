package com.autofinance.api.iam.domain.model.aggregates;

import com.autofinance.api.iam.domain.model.valueobjects.PasswordHash;
import com.autofinance.api.iam.domain.model.valueobjects.UserId;
import com.autofinance.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.autofinance.api.shared.domain.model.valueobjects.Email;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;
import lombok.Getter;

import java.util.UUID;

/**
 * Aggregate root of the IAM context's credentials: a user that belongs to exactly one dealership. Login
 * email and username are globally unique. {@code dealershipId} is a plain FK reference (NOT
 * {@code @TenantId}) — at login the tenant is resolved from a global email/username lookup.
 */
@Getter
@Entity
public class User extends AuditableAbstractAggregateRoot<User, UserId> {

    @EmbeddedId
    private UserId id;

    @Column(name = "dealership_id")
    private UUID dealershipId;

    @Embedded
    private Email email;

    @Column(name = "username")
    private String username;

    @Embedded
    private PasswordHash passwordHash;

    @Version
    @Column(name = "version")
    private long version;

    protected User() {
        // for JPA
    }

    public User(UserId id, UUID dealershipId, Email email, String username, PasswordHash passwordHash) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("Username cannot be null or blank");
        }
        this.id = id;
        this.dealershipId = dealershipId;
        this.email = email;
        this.username = username;
        this.passwordHash = passwordHash;
    }

    @Override
    public UserId getId() {
        return id;
    }
}
