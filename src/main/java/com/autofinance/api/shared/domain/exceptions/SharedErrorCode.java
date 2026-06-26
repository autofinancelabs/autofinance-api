package com.autofinance.api.shared.domain.exceptions;

/** Error catalog for cross-context (shared kernel) domain errors. */
public enum SharedErrorCode implements ErrorCode {

    CURRENCY_MISMATCH(ErrorCategory.VALIDATION);

    private final ErrorCategory category;

    SharedErrorCode(ErrorCategory category) {
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
