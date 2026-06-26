package com.autofinance.api.iam.domain.exceptions;

import com.autofinance.api.shared.domain.exceptions.ErrorCategory;
import com.autofinance.api.shared.domain.exceptions.ErrorCode;

/** Error catalog for the IAM context. The frontend reacts to {@link #code()}. */
public enum IamErrorCode implements ErrorCode {

    DUPLICATE_RUC(ErrorCategory.CONFLICT),
    DUPLICATE_EMAIL(ErrorCategory.CONFLICT),
    DUPLICATE_USERNAME(ErrorCategory.CONFLICT),
    INVALID_CREDENTIALS(ErrorCategory.UNAUTHORIZED);

    private final ErrorCategory category;

    IamErrorCode(ErrorCategory category) {
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
