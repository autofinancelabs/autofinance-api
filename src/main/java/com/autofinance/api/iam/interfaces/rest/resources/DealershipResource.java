package com.autofinance.api.iam.interfaces.rest.resources;

import java.util.UUID;

/** Response view of a dealership account (no credentials echoed). */
public record DealershipResource(
        UUID id,
        String name,
        String ruc,
        String contactEmail
) {
}
