package com.autofinance.api.clients.interfaces.rest.resources;

import java.util.UUID;

/** Response view of a client (the stored snapshot). Contact fields are null when not provided. */
public record ClientResource(
        UUID id,
        String documentType,
        String documentNumber,
        String email,
        String phone,
        String address
) {
}
