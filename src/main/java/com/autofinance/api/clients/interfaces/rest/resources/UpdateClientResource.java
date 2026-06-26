package com.autofinance.api.clients.interfaces.rest.resources;

/**
 * Request body to update a client's contact data. The identity document is immutable, so it is not part
 * of this body; the client id comes from the path. All contact fields are optional.
 */
public record UpdateClientResource(
        String email,
        String phone,
        String address
) {
}
