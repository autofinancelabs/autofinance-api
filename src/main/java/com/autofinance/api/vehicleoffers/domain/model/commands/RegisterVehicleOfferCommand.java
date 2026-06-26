package com.autofinance.api.vehicleoffers.domain.model.commands;

import com.autofinance.api.shared.domain.model.valueobjects.Currency;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Raw inputs to register a vehicle offer. Carries primitives/enums (not value objects) so the
 * application/REST boundary can map flat input directly; the factory builds the VOs. {@code planName}
 * and {@code planInstallments} are optional.
 */
public record RegisterVehicleOfferCommand(
        UUID dealershipId,
        String make,
        String model,
        int year,
        BigDecimal salePrice,
        Currency currency,
        String planName,
        Integer planInstallments
) {
}
