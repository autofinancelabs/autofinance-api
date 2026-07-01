package com.autofinance.api.clients.interfaces.rest.transform;

import com.autofinance.api.clients.domain.model.commands.UpdateClientCommand;
import com.autofinance.api.clients.interfaces.rest.resources.UpdateClientResource;

import java.util.UUID;

/** Builds an {@link UpdateClientCommand} from the path id and the request resource (contact data only). */
public final class UpdateClientCommandFromResourceAssembler {

    private UpdateClientCommandFromResourceAssembler() {
    }

    public static UpdateClientCommand toCommandFromResource(UUID clientId, UpdateClientResource r) {
        return new UpdateClientCommand(clientId, r.firstName(), r.lastName(), r.email(), r.phone(), r.address());
    }
}
