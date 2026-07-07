package com.autofinance.api.clients.interfaces.rest.transform;

import com.autofinance.api.clients.domain.model.aggregates.Client;
import com.autofinance.api.clients.domain.model.valueobjects.ContactInfo;
import com.autofinance.api.clients.domain.model.valueobjects.PersonName;
import com.autofinance.api.clients.interfaces.rest.resources.ClientResource;

/** Builds the response resource from a {@link Client} aggregate. */
public final class ClientResourceFromEntityAssembler {

    private ClientResourceFromEntityAssembler() {
    }

    public static ClientResource toResourceFromEntity(Client c) {
        ContactInfo contact = c.getContactInfo();
        PersonName name = c.getName();
        return new ClientResource(
                c.getId().value(),
                c.getDocumentId().type().name(),
                c.getDocumentId().number(),
                name == null ? null : name.firstName(),
                name == null ? null : name.lastName(),
                contact == null ? null : contact.email(),
                contact == null ? null : contact.phone(),
                contact == null ? null : contact.address()
        );
    }
}
