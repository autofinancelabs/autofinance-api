package com.autofinance.api.vehicleoffers.domain.model.aggregates;

import com.autofinance.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.autofinance.api.shared.domain.model.valueobjects.Money;
import com.autofinance.api.vehicleoffers.domain.exceptions.InvalidVehicleOfferException;
import com.autofinance.api.vehicleoffers.domain.model.events.VehicleOfferRegistered;
import com.autofinance.api.vehicleoffers.domain.model.events.VehicleOfferUpdated;
import com.autofinance.api.shared.domain.model.valueobjects.DealershipId;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.Vehicle;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.Vehicle3dModel;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.VehicleOfferId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;
import lombok.Getter;
import org.hibernate.annotations.TenantId;

import java.util.UUID;

/**
 * Aggregate root of the Vehicle Offers supporting context: the vehicle offer (vehicle + sale price)
 * that the Credit Simulation core consumes by-id. Enforces the sale-price invariant.
 */
@Getter
@Entity
public class VehicleOffer extends AuditableAbstractAggregateRoot<VehicleOffer, VehicleOfferId> {

    @EmbeddedId
    private VehicleOfferId id;

    @TenantId
    @Column(name = "dealership_id")
    private UUID dealershipId;

    @Embedded
    private Vehicle vehicle;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "sale_price_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "sale_price_currency"))
    })
    private Money salePrice;

    /** Optional low-poly 3D model of the offer (null when none was generated). */
    @Embedded
    private Vehicle3dModel model3d;

    @Version
    @Column(name = "version")
    private long version;

    protected VehicleOffer() {
        // for JPA
    }

    public VehicleOffer(VehicleOfferId id, UUID dealershipId, Vehicle vehicle, Money salePrice,
                        Vehicle3dModel model3d) {
        this.id = id;
        this.dealershipId = dealershipId;
        this.vehicle = vehicle;
        this.salePrice = requirePositivePrice(salePrice);
        this.model3d = model3d;
        addDomainEvent(new VehicleOfferRegistered(id, new DealershipId(dealershipId)));
    }

    /** Re-applies the vehicle, sale price and 3D model, enforcing the price invariant. */
    public void update(Vehicle vehicle, Money salePrice, Vehicle3dModel model3d) {
        this.vehicle = vehicle;
        this.salePrice = requirePositivePrice(salePrice);
        this.model3d = model3d;
        addDomainEvent(new VehicleOfferUpdated(id, new DealershipId(dealershipId)));
    }

    /** Whether this offer has a 3D model configured. */
    public boolean has3dModel() {
        return model3d != null;
    }

    private static Money requirePositivePrice(Money salePrice) {
        if (salePrice == null || !salePrice.isPositive()) {
            throw new InvalidVehicleOfferException("sale price must be > 0");
        }
        return salePrice;
    }

    @Override
    public VehicleOfferId getId() {
        return id;
    }
}
