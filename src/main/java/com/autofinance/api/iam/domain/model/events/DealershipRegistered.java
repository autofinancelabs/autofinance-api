package com.autofinance.api.iam.domain.model.events;

import com.autofinance.api.shared.domain.model.valueobjects.DealershipId;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/** Raised when a dealership account (with its first user) has been registered. Internal in v1. */
@Getter
public class DealershipRegistered extends ApplicationEvent {

    private final DealershipId dealershipId;

    public DealershipRegistered(DealershipId dealershipId) {
        super(dealershipId);
        this.dealershipId = dealershipId;
    }
}
