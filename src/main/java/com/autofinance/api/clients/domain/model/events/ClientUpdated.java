package com.autofinance.api.clients.domain.model.events;

import com.autofinance.api.clients.domain.model.valueobjects.ClientId;
import com.autofinance.api.shared.domain.model.valueobjects.DealershipId;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/** Raised when a client's contact data has been updated. Internal in v1. */
@Getter
public class ClientUpdated extends ApplicationEvent {

    private final ClientId clientId;
    private final DealershipId dealershipId;

    public ClientUpdated(ClientId clientId, DealershipId dealershipId) {
        super(clientId);
        this.clientId = clientId;
        this.dealershipId = dealershipId;
    }
}
