package com.autofinance.api.iam.interfaces.rest.transform;

import com.autofinance.api.iam.domain.model.commands.RegisterDealershipCommand;
import com.autofinance.api.iam.interfaces.rest.resources.RegisterDealershipResource;

/** Builds a {@link RegisterDealershipCommand} from the request resource (no tenant — none exists yet). */
public final class RegisterDealershipCommandFromResourceAssembler {

    private RegisterDealershipCommandFromResourceAssembler() {
    }

    public static RegisterDealershipCommand toCommandFromResource(RegisterDealershipResource r) {
        return new RegisterDealershipCommand(
                r.name(),
                r.ruc(),
                r.contactEmail(),
                r.userEmail(),
                r.username(),
                r.password()
        );
    }
}
