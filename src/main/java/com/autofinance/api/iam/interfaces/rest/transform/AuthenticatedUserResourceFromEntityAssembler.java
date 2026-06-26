package com.autofinance.api.iam.interfaces.rest.transform;

import com.autofinance.api.iam.domain.services.AuthenticatedUser;
import com.autofinance.api.iam.interfaces.rest.resources.AuthenticatedUserResource;

/** Builds the sign-in response from the authenticated user + token. */
public final class AuthenticatedUserResourceFromEntityAssembler {

    private AuthenticatedUserResourceFromEntityAssembler() {
    }

    public static AuthenticatedUserResource toResourceFromEntity(AuthenticatedUser authenticated) {
        var user = authenticated.user();
        return new AuthenticatedUserResource(
                user.getId().value(),
                user.getUsername(),
                user.getDealershipId(),
                authenticated.token());
    }
}
