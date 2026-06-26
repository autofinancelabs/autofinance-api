package com.autofinance.api.iam.domain.services;

import com.autofinance.api.iam.domain.model.commands.SignInCommand;

/** Application-service port (domain) for the IAM authentication use case. */
public interface AuthenticationCommandService {

    /** Verifies credentials and issues a token, or throws {@code InvalidCredentialsException}. */
    AuthenticatedUser handle(SignInCommand command);
}
