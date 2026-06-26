package com.autofinance.api.shared.domain.exceptions;

/**
 * A stable, machine-readable error code. The frontend reacts to {@link #code()} (not the HTTP status
 * nor the human message); {@link #category()} lets the web layer pick the HTTP status without the
 * domain knowing about HTTP. Each bounded context owns its own catalog (an enum implementing this).
 */
public interface ErrorCode {

    /** Stable identifier the frontend keys on (e.g. {@code "INVALID_VEHICLE_OFFER"}). */
    String code();

    /** HTTP-agnostic category, mapped to an HTTP status by the web layer. */
    ErrorCategory category();
}
