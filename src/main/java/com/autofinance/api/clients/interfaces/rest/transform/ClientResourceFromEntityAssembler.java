package com.autofinance.api.clients.interfaces.rest.transform;

import com.autofinance.api.clients.domain.model.aggregates.Client;
import com.autofinance.api.clients.domain.model.valueobjects.ContactInfo;
import com.autofinance.api.clients.interfaces.rest.resources.ClientResource;

/** Builds the response resource from a {@link Client} aggregate. */
public final class ClientResourceFromEntityAssembler {

    private ClientResourceFromEntityAssembler() {
    }

    public static ClientResource toResourceFromEntity(Client c) {
        ContactInfo contact = c.getContactInfo();
        return new ClientResource(
                c.getId().value(),
                c.getDocumentId().type().name(),
                c.getDocumentId().number(),
                contact == null ? null : contact.email(),
                contact == null ? null : contact.phone(),
                contact == null ? null : contact.address()
        );
    }
}
