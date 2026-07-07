package com.autofinance.api.vehicleoffers.domain.model.commands;

import com.autofinance.api.shared.domain.model.valueobjects.Currency;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Raw inputs to update an existing vehicle offer. Carries primitives/enums (not value objects).
 */
public record UpdateVehicleOfferCommand(
        UUID vehicleOfferId,
        String make,
        String model,
        int year,
        BigDecimal salePrice,
        Currency currency
) {
}
