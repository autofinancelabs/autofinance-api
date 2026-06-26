package com.autofinance.api.clients.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * A client's identity document: type (DNI/CE/PASAPORTE) plus number. Immutable; its value identifies
 * the client within a dealership (uniqueness invariant), so equality is by type + number.
 */
@Embeddable
public record DocumentId(
        @Enumerated(EnumType.STRING) @Column(name = "document_id_type") DocumentType type,
        @Column(name = "document_id_number") String number
) {
    public DocumentId {
        if (type == null) {
            throw new IllegalArgumentException("DocumentId type cannot be null");
        }
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("DocumentId number cannot be null or blank");
        }
    }
}
