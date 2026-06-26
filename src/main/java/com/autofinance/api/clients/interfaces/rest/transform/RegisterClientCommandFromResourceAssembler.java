package com.autofinance.api.clients.interfaces.rest.transform;

import com.autofinance.api.clients.domain.model.commands.RegisterClientCommand;
import com.autofinance.api.clients.domain.model.valueobjects.DocumentType;
import com.autofinance.api.clients.interfaces.rest.resources.RegisterClientResource;

import java.util.UUID;

/**
 * Builds a {@link RegisterClientCommand} from the request resource and the current dealership (tenant,
 * from the header). Translates the wire document-type String into the domain enum — an unknown value
 * raises {@code IllegalArgumentException}, mapped to 400 by the global handler.
 */
public final class RegisterClientCommandFromResourceAssembler {

    private RegisterClientCommandFromResourceAssembler() {
    }

    public static RegisterClientCommand toCommandFromResource(UUID dealershipId, RegisterClientResource r) {
        return new RegisterClientCommand(
                dealershipId,
                DocumentType.valueOf(r.documentType()),
                r.documentNumber(),
                r.email(),
                r.phone(),
                r.address()
        );
    }
}
