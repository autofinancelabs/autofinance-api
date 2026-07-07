package com.autofinance.api.vehicleoffers.domain.model.aggregates;

import com.autofinance.api.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import com.autofinance.api.shared.domain.model.valueobjects.Money;
import com.autofinance.api.vehicleoffers.domain.exceptions.InvalidVehicleOfferException;
import com.autofinance.api.vehicleoffers.domain.model.events.VehicleOfferRegistered;
import com.autofinance.api.vehicleoffers.domain.model.events.VehicleOfferUpdated;
import com.autofinance.api.shared.domain.model.valueobjects.DealershipId;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.Vehicle;
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

    @Version
    @Column(name = "version")
    private long version;

    protected VehicleOffer() {
        // for JPA
    }

    public VehicleOffer(VehicleOfferId id, UUID dealershipId, Vehicle vehicle, Money salePrice) {
        this.id = id;
        this.dealershipId = dealershipId;
        this.vehicle = vehicle;
        this.salePrice = requirePositivePrice(salePrice);
        addDomainEvent(new VehicleOfferRegistered(id, new DealershipId(dealershipId)));
    }

    /** Re-applies the vehicle and sale price, enforcing the price invariant. */
    public void update(Vehicle vehicle, Money salePrice) {
        this.vehicle = vehicle;
        this.salePrice = requirePositivePrice(salePrice);
        addDomainEvent(new VehicleOfferUpdated(id, new DealershipId(dealershipId)));
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
