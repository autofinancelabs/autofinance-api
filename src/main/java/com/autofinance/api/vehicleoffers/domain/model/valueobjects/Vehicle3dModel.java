package com.autofinance.api.vehicleoffers.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

/**
 * The optional low-poly 3D model of a vehicle offer: its silhouette preset, body/window colors (free hex)
 * and cosmetic options. Purely presentational — no business invariants. Embedded in {@code VehicleOffer};
 * when all columns are null Hibernate treats the whole value as absent (no 3D model). Colors are stored as
 * hex strings and the booleans as nullable wrappers so rows created before an option existed stay valid
 * (a null option reads as "off").
 */
@Embeddable
public record Vehicle3dModel(
        @Enumerated(EnumType.STRING) @Column(name = "model_3d_preset") Model3dPreset preset,
        @Column(name = "model_3d_body_color") String bodyColor,
        @Column(name = "model_3d_window_color") String windowColor,
        @Column(name = "model_3d_sport_wheels") Boolean sportWheels,
        @Column(name = "model_3d_spoiler") Boolean spoiler,
        @Column(name = "model_3d_pano_roof") Boolean panoRoof,
        @Column(name = "model_3d_plate") String plateText
) {
}
