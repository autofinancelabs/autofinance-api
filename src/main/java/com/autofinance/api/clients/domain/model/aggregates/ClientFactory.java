package com.autofinance.api.clients.domain.model.aggregates;

import com.autofinance.api.clients.domain.model.commands.RegisterClientCommand;
import com.autofinance.api.clients.domain.model.valueobjects.ClientId;
import com.autofinance.api.clients.domain.model.valueobjects.ContactInfo;
import com.autofinance.api.clients.domain.model.valueobjects.DocumentId;

/**
 * Domain factory for the {@link Client} aggregate: assembles a valid client from raw inputs and
 * generates its identity. Co-located with the aggregate it creates.
 */
public class ClientFactory {

    public Client create(RegisterClientCommand command) {
        DocumentId documentId = new DocumentId(command.documentType(), command.documentNumber());
        ContactInfo contactInfo = ContactInfo.of(command.email(), command.phone(), command.address());

        return new Client(ClientId.generate(), command.dealershipId(), documentId, contactInfo);
    }
}
