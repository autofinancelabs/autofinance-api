package com.autofinance.api.shared.infrastructure.multitenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;

import java.util.UUID;

/**
 * Resolves the current dealership (tenant) for Hibernate's {@code @TenantId} discriminator from
 * {@link TenantContext}. Falls back to a fixed default tenant when none is set (e.g. boot,
 * non-request threads), so the persistence layer always has an identifier.
 */
public class CurrentTenantResolver implements CurrentTenantIdentifierResolver<UUID> {

    public static final UUID DEFAULT_TENANT = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Override
    public UUID resolveCurrentTenantIdentifier() {
        UUID tenant = TenantContext.getTenant();
        return tenant != null ? tenant : DEFAULT_TENANT;
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return false;
    }
}
