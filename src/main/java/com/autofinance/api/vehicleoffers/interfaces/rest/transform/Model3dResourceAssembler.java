package com.autofinance.api.vehicleoffers.interfaces.rest.transform;

import com.autofinance.api.vehicleoffers.domain.model.valueobjects.Model3dPreset;
import com.autofinance.api.vehicleoffers.domain.model.valueobjects.Vehicle3dModel;
import com.autofinance.api.vehicleoffers.interfaces.rest.resources.Model3dResource;

/**
 * Maps the optional 3D model between its wire shape ({@link Model3dResource}) and the domain value object
 * ({@link Vehicle3dModel}), null-safely (a null on either side means "no 3D model"). The preset String is
 * translated to the domain enum here — an unknown value raises {@code IllegalArgumentException}, mapped to
 * 400 by the global handler.
 */
public final class Model3dResourceAssembler {

    private Model3dResourceAssembler() {
    }

    public static Vehicle3dModel toValueObject(Model3dResource r) {
        if (r == null) {
            return null;
        }
        return new Vehicle3dModel(
                r.preset() == null ? null : Model3dPreset.valueOf(r.preset()),
                r.bodyColor(),
                r.windowColor(),
                r.sportWheels(),
                r.spoiler(),
                r.panoRoof(),
                r.plateText()
        );
    }

    public static Model3dResource toResource(Vehicle3dModel m) {
        if (m == null) {
            return null;
        }
        return new Model3dResource(
                m.preset() == null ? null : m.preset().name(),
                m.bodyColor(),
                m.windowColor(),
                m.sportWheels(),
                m.spoiler(),
                m.panoRoof(),
                m.plateText()
        );
    }
}
