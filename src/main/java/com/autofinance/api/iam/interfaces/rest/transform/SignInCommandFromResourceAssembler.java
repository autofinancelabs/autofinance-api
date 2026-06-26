package com.autofinance.api.iam.interfaces.rest.transform;

import com.autofinance.api.iam.domain.model.commands.SignInCommand;
import com.autofinance.api.iam.interfaces.rest.resources.SignInResource;

/** Builds a {@link SignInCommand} from the request resource. */
public final class SignInCommandFromResourceAssembler {

    private SignInCommandFromResourceAssembler() {
    }

    public static SignInCommand toCommandFromResource(SignInResource r) {
        return new SignInCommand(r.identifier(), r.password());
    }
}
