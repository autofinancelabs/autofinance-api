package com.autofinance.api.creditsimulation.interfaces.rest;

import org.springframework.http.HttpStatus;

import java.net.URI;

/**
 * Stable, machine-readable error catalog. The frontend reacts to {@link #code()} (not the HTTP status
 * nor the human {@code detail}); {@link #type()} is a stable URI per category for the ProblemDetail.
 */
public enum ErrorCode {

    VALIDATION_FAILED(HttpStatus.BAD_REQUEST),
    MALFORMED_REQUEST(HttpStatus.BAD_REQUEST),
    MISSING_TENANT(HttpStatus.BAD_REQUEST),
    INVALID_SIMULATION_CONFIGURATION(HttpStatus.BAD_REQUEST),
    PERCENTAGE_OUT_OF_RANGE(HttpStatus.BAD_REQUEST),
    CURRENCY_MISMATCH(HttpStatus.BAD_REQUEST),
    MISSING_CAPITALIZATION(HttpStatus.BAD_REQUEST),
    SCHEDULE_NOT_BALANCED(HttpStatus.UNPROCESSABLE_ENTITY),
    IRR_NOT_BRACKETED(HttpStatus.UNPROCESSABLE_ENTITY),
    SIMULATION_NOT_FOUND(HttpStatus.NOT_FOUND),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR);

    private static final String TYPE_BASE = "https://api.autofinance/errors/";

    private final HttpStatus status;

    ErrorCode(HttpStatus status) {
        this.status = status;
    }

    public HttpStatus status() {
        return status;
    }

    public String code() {
        return name();
    }

    public URI type() {
        return URI.create(TYPE_BASE + name().toLowerCase().replace('_', '-'));
    }
}
