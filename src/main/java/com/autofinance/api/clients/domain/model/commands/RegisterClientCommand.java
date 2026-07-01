package com.autofinance.api.clients.domain.model.commands;

import com.autofinance.api.clients.domain.model.valueobjects.DocumentType;

import java.util.UUID;

/**
 * Raw inputs to register a client. Carries primitives/enums (not value objects) so the
 * application/REST boundary can map flat input directly; the factory builds the VOs. Contact fields are
 * optional.
 */
public record RegisterClientCommand(
        UUID dealershipId,
        DocumentType documentType,
        String documentNumber,
        String firstName,
        String lastName,
        String email,
        String phone,
        String address
) {
}
