package com.autofinance.api.clients.domain.exceptions;

import com.autofinance.api.shared.domain.exceptions.ErrorCategory;
import com.autofinance.api.shared.domain.exceptions.ErrorCode;

/** Error catalog for the Clients context. The frontend reacts to {@link #code()}. */
public enum ClientErrorCode implements ErrorCode {

    DUPLICATE_CLIENT_DOCUMENT(ErrorCategory.CONFLICT);

    private final ErrorCategory category;

    ClientErrorCode(ErrorCategory category) {
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
