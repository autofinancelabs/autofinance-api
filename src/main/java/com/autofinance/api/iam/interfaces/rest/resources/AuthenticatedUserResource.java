package com.autofinance.api.iam.interfaces.rest.resources;

import java.util.UUID;

/** Sign-in response: the authenticated user's identity and the bearer token to use on later requests. */
public record AuthenticatedUserResource(
        UUID userId,
        String username,
        UUID dealershipId,
        String token
) {
}
