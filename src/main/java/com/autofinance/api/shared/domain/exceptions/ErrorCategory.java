package com.autofinance.api.shared.domain.exceptions;

/**
 * HTTP-agnostic classification of a domain error. The web layer maps each category to an HTTP status
 * (see the shared {@code GlobalExceptionHandler}); the domain stays free of HTTP concepts.
 */
public enum ErrorCategory {
    /** Invalid input / broken invariant the caller can fix. */
    VALIDATION,
    /** Well-formed request that cannot be processed in the current state. */
    UNPROCESSABLE,
    /** A referenced resource does not exist. */
    NOT_FOUND,
    /** The request conflicts with the current state of a resource. */
    CONFLICT,
    /** Authentication is missing or invalid. */
    UNAUTHORIZED,
    /** Authenticated, but not allowed to perform the action. */
    FORBIDDEN,
    /** Unexpected server-side failure. */
    INTERNAL
}
