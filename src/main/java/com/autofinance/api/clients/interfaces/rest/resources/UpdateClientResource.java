package com.autofinance.api.clients.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

/**
 * Request body to update a client's editable data: the name (required) and contact fields (optional).
 * The identity document is immutable, so it is not part of this body; the client id comes from the path.
 */
public record UpdateClientResource(
        @NotBlank String firstName,
        @NotBlank String lastName,
        String email,
        String phone,
        String address
) {
}
