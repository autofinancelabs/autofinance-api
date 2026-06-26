package com.autofinance.api.clients.interfaces.rest.resources;

import com.autofinance.api.clients.domain.model.valueobjects.DocumentType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Request body to register a client. Mirrors {@code RegisterClientCommand} minus the dealership, which
 * comes from the {@code X-Dealership-Id} header (the tenant). {@code documentType} stays a String on the
 * wire (mapped to the domain enum in the assembler) but is documented with its allowed values. Contact
 * fields are optional.
 */
public record RegisterClientResource(
        @NotBlank @Schema(implementation = DocumentType.class) String documentType,
        @NotBlank String documentNumber,
        String email,
        String phone,
        String address
) {
}
