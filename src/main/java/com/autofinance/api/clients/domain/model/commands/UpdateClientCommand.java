package com.autofinance.api.clients.domain.model.commands;

import java.util.UUID;

/**
 * Raw inputs to update a client's contact data. The identity document is immutable, so it is not part of
 * this command; contact fields are optional.
 */
public record UpdateClientCommand(
        UUID clientId,
        String firstName,
        String lastName,
        String email,
        String phone,
        String address
) {
}
