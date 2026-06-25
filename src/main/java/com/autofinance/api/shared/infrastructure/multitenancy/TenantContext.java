package com.autofinance.api.shared.infrastructure.multitenancy;

import java.util.UUID;

/**
 * Holds the current tenant (dealership) for the running thread. Set by the inbound adapter
 * (a future auth filter) or explicitly in tests; read by {@link CurrentTenantResolver}.
 */
public final class TenantContext {

    private static final ThreadLocal<UUID> CURRENT = new ThreadLocal<>();

    private TenantContext() {
    }

    public static void setTenant(UUID dealershipId) {
        CURRENT.set(dealershipId);
    }

    public static UUID getTenant() {
        return CURRENT.get();
    }

    public static void clear() {
        CURRENT.remove();
    }
}
