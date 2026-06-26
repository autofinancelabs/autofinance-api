package com.autofinance.api.vehicleoffers.domain.exceptions;

import com.autofinance.api.shared.domain.exceptions.ErrorCategory;
import com.autofinance.api.shared.domain.exceptions.ErrorCode;

/** Error catalog for the Vehicle Offers context. The frontend reacts to {@link #code()}. */
public enum VehicleOfferErrorCode implements ErrorCode {

    INVALID_VEHICLE_OFFER(ErrorCategory.VALIDATION);

    private final ErrorCategory category;

    VehicleOfferErrorCode(ErrorCategory category) {
        this.category = category;
    }

    @Override
    public String code() {
        return name();
    }

    @Override
    public ErrorCategory category() {
        return category;
    }
}
