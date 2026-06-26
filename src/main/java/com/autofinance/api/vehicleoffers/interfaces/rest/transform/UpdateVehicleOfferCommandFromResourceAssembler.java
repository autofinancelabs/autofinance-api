package com.autofinance.api.vehicleoffers.interfaces.rest.transform;

import com.autofinance.api.shared.domain.model.valueobjects.Currency;
import com.autofinance.api.vehicleoffers.domain.model.commands.UpdateVehicleOfferCommand;
import com.autofinance.api.vehicleoffers.interfaces.rest.resources.UpdateVehicleOfferResource;

import java.util.UUID;

/**
 * Builds an {@link UpdateVehicleOfferCommand} from the path id and the request resource. Translates the
 * wire currency String into the domain enum — an unknown value raises {@code IllegalArgumentException},
 * mapped to 400 by the global handler.
 */
public final class UpdateVehicleOfferCommandFromResourceAssembler {

    private UpdateVehicleOfferCommandFromResourceAssembler() {
    }

    public static UpdateVehicleOfferCommand toCommandFromResource(UUID vehicleOfferId,
                                                                  UpdateVehicleOfferResource r) {
        return new UpdateVehicleOfferCommand(
                vehicleOfferId,
                r.make(),
                r.model(),
                r.year(),
                r.salePrice(),
                Currency.valueOf(r.currency()),
                r.planName(),
                r.planInstallments()
        );
    }
}
