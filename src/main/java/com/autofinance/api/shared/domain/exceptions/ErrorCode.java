package com.autofinance.api.shared.domain.exceptions;

/**
 * A stable, machine-readable error code. The frontend reacts to {@link #code()} (not the HTTP status
 * nor the human message); {@link #category()} lets the web layer pick the HTTP status without the
 * domain knowing about HTTP. Each bounded context owns its own catalog (an enum implementing this).
 * <p>
 * Adding a value (or a whole new catalog) requires documenting it in {@code docs/architecture/error-codes.md}
 * — {@code ErrorCodesDocumentedTest} fails until every code appears there.
 */
public interface ErrorCode {

    /** Stable identifier the frontend keys on (e.g. {@code "INVALID_VEHICLE_OFFER"}). */
    String code();

    /** HTTP-agnostic category, mapped to an HTTP status by the web layer. */
    ErrorCategory category();
}
