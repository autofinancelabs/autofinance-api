package com.autofinance.api.creditsimulation.interfaces.rest.resources;

import com.autofinance.api.creditsimulation.interfaces.rest.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Documentation-only view of the RFC 9457 error body (the API actually returns a {@code ProblemDetail}).
 * Exists so OpenAPI/Scalar shows the {@code code} catalog and the field-errors shape to consumers.
 */
@Schema(name = "ProblemDetail", description = "RFC 9457 error response (media type application/problem+json)")
public record ApiErrorSchema(
        @Schema(description = "URI that categorizes the error",
                example = "https://api.autofinance/errors/validation-failed") String type,
        @Schema(description = "Short, human-readable summary", example = "Bad Request") String title,
        @Schema(description = "HTTP status code", example = "400") int status,
        @Schema(description = "Developer-facing detail — do NOT show to end users; translate by 'code' instead",
                example = "salePrice must be greater than 0") String detail,
        @Schema(description = "The request path", example = "/api/v1/credit-simulations") String instance,
        @Schema(description = "Stable, machine-readable error code the frontend reacts to") ErrorCode code,
        @Schema(description = "When the error occurred (ISO-8601 UTC)",
                example = "2026-06-25T08:19:01.788Z") String timestamp,
        @Schema(description = "Field-level violations (present only for validation errors)") List<FieldError> errors
) {
    @Schema(name = "FieldError", description = "A single field validation violation")
    public record FieldError(
            @Schema(example = "salePrice") String field,
            @Schema(example = "must be greater than 0") String message
    ) {
    }
}
