package com.autofinance.api.shared.infrastructure.security;

import java.util.UUID;

/**
 * Contract the authenticated principal exposes so the shared layer can read the current user's identity
 * and tenant without depending on the IAM context. Implemented by IAM's security principal.
 */
public interface TenantPrincipal {

    UUID userId();

    UUID dealershipId();
}
