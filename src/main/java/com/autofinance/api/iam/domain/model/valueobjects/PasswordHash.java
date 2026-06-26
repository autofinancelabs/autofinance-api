package com.autofinance.api.iam.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * An opaque, already-hashed password. The domain never sees or stores plaintext; hashing the raw
 * password is an application/infrastructure concern (the security pass).
 */
@Embeddable
public record PasswordHash(@Column(name = "password_hash") String value) {
    public PasswordHash {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("PasswordHash cannot be null or blank");
        }
    }
}
