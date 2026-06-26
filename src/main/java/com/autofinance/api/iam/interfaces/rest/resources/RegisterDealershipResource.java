package com.autofinance.api.iam.interfaces.rest.resources;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Request body to register a dealership account and its first user. No tenant header: this is the front
 * door that creates the dealership (tenant). Bean validation gives clean 400 field errors; the domain VOs
 * are the backstop.
 */
public record RegisterDealershipResource(
        @NotBlank String name,
        @NotBlank @Pattern(regexp = "\\d{11}", message = "RUC must be exactly 11 digits") String ruc,
        String contactEmail,
        @NotBlank @Email String userEmail,
        @NotBlank String username,
        @NotBlank String password
) {
}
