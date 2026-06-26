package com.autofinance.api.clients.domain.model.events;

import com.autofinance.api.clients.domain.model.valueobjects.ClientId;
import com.autofinance.api.shared.domain.model.valueobjects.DealershipId;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/** Raised when a client has been registered. Internal in v1. */
@Getter
public class ClientRegistered extends ApplicationEvent {

    private final ClientId clientId;
    private final DealershipId dealershipId;

    public ClientRegistered(ClientId clientId, DealershipId dealershipId) {
        super(clientId);
        this.clientId = clientId;
        this.dealershipId = dealershipId;
    }
}
