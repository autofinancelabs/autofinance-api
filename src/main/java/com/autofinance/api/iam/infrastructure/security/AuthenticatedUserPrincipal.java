package com.autofinance.api.iam.infrastructure.security;

import com.autofinance.api.shared.infrastructure.security.TenantPrincipal;

import java.util.UUID;

/** The authenticated principal carried in the security context (built from the JWT claims). */
public record AuthenticatedUserPrincipal(UUID userId, UUID dealershipId, String username)
        implements TenantPrincipal {
}
