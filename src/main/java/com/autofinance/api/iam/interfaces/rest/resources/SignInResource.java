package com.autofinance.api.iam.interfaces.rest.resources;

import jakarta.validation.constraints.NotBlank;

/** Sign-in request: identifier is the username or the email. */
public record SignInResource(
        @NotBlank String identifier,
        @NotBlank String password
) {
}
