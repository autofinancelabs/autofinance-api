package com.autofinance.api.clients.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * A client's personal name: given names ({@code firstName}, e.g. "Juan Carlos") and family names
 * ({@code lastName}, e.g. "Pérez García"), each stored whole. Required and trimmed on construction.
 * Unlike the {@link DocumentId} it is not part of the identity, so it can change.
 */
@Embeddable
public record PersonName(
        @Column(name = "first_name") String firstName,
        @Column(name = "last_name") String lastName
) {
    public PersonName {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("PersonName firstName cannot be null or blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("PersonName lastName cannot be null or blank");
        }
        firstName = firstName.trim();
        lastName = lastName.trim();
    }
}
