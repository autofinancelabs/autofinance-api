package com.autofinance.api.vehicleoffers.domain.model.events;

import com.autofinance.api.shared.domain.model.valueobjects.DealershipId;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.VehicleOfferId;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/** Raised when a vehicle offer has been registered. Internal in v1. */
@Getter
public class VehicleOfferRegistered extends ApplicationEvent {

    private final VehicleOfferId vehicleOfferId;
    private final DealershipId dealershipId;

    public VehicleOfferRegistered(VehicleOfferId vehicleOfferId, DealershipId dealershipId) {
        super(vehicleOfferId);
        this.vehicleOfferId = vehicleOfferId;
        this.dealershipId = dealershipId;
    }
}
