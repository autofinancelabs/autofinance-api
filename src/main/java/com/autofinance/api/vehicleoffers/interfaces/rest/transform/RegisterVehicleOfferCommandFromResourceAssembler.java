package com.autofinance.api.vehicleoffers.interfaces.rest.transform;

import com.autofinance.api.shared.domain.model.valueobjects.Currency;
import com.autofinance.api.vehicleoffers.domain.model.commands.RegisterVehicleOfferCommand;
import com.autofinance.api.vehicleoffers.interfaces.rest.resources.RegisterVehicleOfferResource;

import java.util.UUID;

/**
 * Builds a {@link RegisterVehicleOfferCommand} from the request resource and the current dealership
 * (tenant, from the header). Translates the wire currency String into the domain enum — an unknown
 * value raises {@code IllegalArgumentException}, mapped to 400 by the global handler. The optional 3D
 * model is mapped null-safely by {@link Model3dResourceAssembler} (absent means no 3D model).
 */
public final class RegisterVehicleOfferCommandFromResourceAssembler {

    private RegisterVehicleOfferCommandFromResourceAssembler() {
    }

    public static RegisterVehicleOfferCommand toCommandFromResource(UUID dealershipId,
                                                                    RegisterVehicleOfferResource r) {
        return new RegisterVehicleOfferCommand(
                dealershipId,
                r.make(),
                r.model(),
                r.year(),
                r.salePrice(),
                Currency.valueOf(r.currency()),
                Model3dResourceAssembler.toValueObject(r.model3d())
        );
    }
}
