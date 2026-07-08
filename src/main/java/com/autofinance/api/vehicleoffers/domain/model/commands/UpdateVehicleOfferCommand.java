package com.autofinance.api.vehicleoffers.domain.model.commands;

import com.autofinance.api.shared.domain.model.valueobjects.Currency;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.Vehicle3dModel;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Raw inputs to update an existing vehicle offer. Carries primitives/enums (not value objects). The
 * optional 3D model is an immutable value object ({@code null} to clear it).
 */
public record UpdateVehicleOfferCommand(
        UUID vehicleOfferId,
        String make,
        String model,
        int year,
        BigDecimal salePrice,
        Currency currency,
        Vehicle3dModel model3d
) {
}
