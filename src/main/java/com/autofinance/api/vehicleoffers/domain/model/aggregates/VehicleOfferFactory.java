package com.autofinance.api.vehicleoffers.domain.model.aggregates;

import com.autofinance.api.shared.domain.model.valueobjects.Money;
import com.autofinance.api.vehicleoffers.domain.model.commands.RegisterVehicleOfferCommand;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.Plan;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.Vehicle;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.VehicleOfferId;

/**
 * Domain factory for the {@link VehicleOffer} aggregate: assembles a valid offer from raw inputs and
 * generates its identity. Co-located with the aggregate it creates.
 */
public class VehicleOfferFactory {

    public VehicleOffer create(RegisterVehicleOfferCommand command) {
        Vehicle vehicle = new Vehicle(command.make(), command.model(), command.year());
        Money salePrice = new Money(command.salePrice(), command.currency());
        Plan plan = Plan.of(command.planName(), command.planInstallments());

        return new VehicleOffer(
                VehicleOfferId.generate(), command.dealershipId(), vehicle, salePrice, plan);
    }
}
