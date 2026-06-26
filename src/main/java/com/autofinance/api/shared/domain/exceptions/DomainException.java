package com.autofinance.api.shared.domain.exceptions;

/**
 * Base type for all domain invariant violations. Carries a stable {@link ErrorCode} (a domain-level
 * identifier, free of HTTP) that the shared web advice turns into an RFC 9457 response. Adding a new
 * domain exception requires no change to the handler — it only declares its code.
 */
public abstract class DomainException extends RuntimeException {

    private final transient ErrorCode errorCode;

    protected DomainException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ErrorCode errorCode() {
        return errorCode;
    }
}
