package com.autofinance.api.vehicleoffers.infrastructure.persistence.jpa.repositories;

import com.autofinance.api.vehicleoffers.domain.model.aggregates.VehicleOffer;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.VehicleOfferId;
import com.autofinance.api.vehicleoffers.domain.repositories.VehicleOfferRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA adapter satisfying the {@link VehicleOfferRepository} port. {@code save},
 * {@code findById} and {@code findAll} are all inherited from {@link JpaRepository}. All reads/writes
 * are tenant-scoped by Hibernate {@code @TenantId}.
 */
@Repository
public interface VehicleOfferJpaRepository
        extends JpaRepository<VehicleOffer, VehicleOfferId>, VehicleOfferRepository {
}
