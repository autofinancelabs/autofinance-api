package com.autofinance.api.shared.interfaces.rest;

import java.util.UUID;

/**
 * Inbound-side access to the authenticated caller, so controllers take the dealership (tenant) from the
 * authenticated principal instead of a client-supplied header. Backed by the security context.
 */
public interface CurrentUser {

    UUID dealershipId();

    UUID userId();
}
