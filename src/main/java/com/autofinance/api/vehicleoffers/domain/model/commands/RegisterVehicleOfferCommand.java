package com.autofinance.api.vehicleoffers.domain.model.commands;

import com.autofinance.api.shared.domain.model.valueobjects.Currency;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.Vehicle3dModel;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Raw inputs to register a vehicle offer. Carries primitives/enums (not value objects) so the
 * application/REST boundary can map flat input directly; the factory builds the VOs. The optional 3D
 * model is already an immutable value object ({@code null} when the user did not generate one).
 */
public record RegisterVehicleOfferCommand(
        UUID dealershipId,
        String make,
        String model,
        int year,
        BigDecimal salePrice,
        Currency currency,
        Vehicle3dModel model3d
) {
}
