package com.autofinance.api.clients.interfaces.acl;

import java.util.UUID;

/**
 * Anti-corruption boundary for other contexts (e.g. Credit Simulation) to validate a client by id.
 * Tenant-scoped: only clients of the current dealership are visible.
 */
public interface ClientsContextFacade {

    boolean existsById(UUID clientId);
}
