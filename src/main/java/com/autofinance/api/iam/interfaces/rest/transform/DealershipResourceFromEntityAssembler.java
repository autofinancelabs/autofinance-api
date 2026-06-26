package com.autofinance.api.iam.interfaces.rest.transform;

import com.autofinance.api.iam.domain.model.aggregates.Dealership;
import com.autofinance.api.iam.interfaces.rest.resources.DealershipResource;

/** Builds the response resource from a {@link Dealership} aggregate. */
public final class DealershipResourceFromEntityAssembler {

    private DealershipResourceFromEntityAssembler() {
    }

    public static DealershipResource toResourceFromEntity(Dealership d) {
        return new DealershipResource(
                d.getId().value(),
                d.getName(),
                d.getRuc().value(),
                d.getContactEmail()
        );
    }
}
