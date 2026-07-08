package com.autofinance.api.vehicleoffers.interfaces.rest.transform;

import com.autofinance.api.shared.domain.model.valueobjects.Money;
import com.autofinance.api.vehicleoffers.domain.model.aggregates.VehicleOffer;
import com.autofinance.api.vehicleoffers.interfaces.rest.resources.MoneyResource;
import com.autofinance.api.vehicleoffers.interfaces.rest.resources.VehicleOfferResource;

/** Builds the response resource from a {@link VehicleOffer} aggregate. */
public final class VehicleOfferResourceFromEntityAssembler {

    private VehicleOfferResourceFromEntityAssembler() {
    }

    public static VehicleOfferResource toResourceFromEntity(VehicleOffer o) {
        return new VehicleOfferResource(
                o.getId().value(),
                o.getVehicle().make(),
                o.getVehicle().model(),
                o.getVehicle().year(),
                money(o.getSalePrice()),
                Model3dResourceAssembler.toResource(o.getModel3d())
        );
    }

    private static MoneyResource money(Money m) {
        return new MoneyResource(m.amount(), m.currency().name());
    }
}
