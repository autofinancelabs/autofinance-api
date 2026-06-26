package com.autofinance.api.shared.interfaces.rest;

import com.autofinance.api.shared.domain.exceptions.ErrorCategory;
import com.autofinance.api.shared.domain.exceptions.ErrorCode;

/**
 * Error codes produced by the web layer itself (not by the domain): bean-validation failures, malformed
 * bodies, a missing tenant header and the last-resort fallback. Implements the same {@link ErrorCode}
 * contract as the domain catalogs so the handler treats every code uniformly.
 */
public enum WebErrorCode implements ErrorCode {

    VALIDATION_FAILED(ErrorCategory.VALIDATION),
    MALFORMED_REQUEST(ErrorCategory.VALIDATION),
    MISSING_TENANT(ErrorCategory.VALIDATION),
    INTERNAL_ERROR(ErrorCategory.INTERNAL);

    private final ErrorCategory category;

    WebErrorCode(ErrorCategory category) {
        this.category = category;
    }

    @Override
    public String code() {
        return name();
    }

    @Override
    public ErrorCategory category() {
        return category;
    }
}
