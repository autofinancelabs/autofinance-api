package com.autofinance.api.iam.domain.exceptions;

import com.autofinance.api.shared.domain.exceptions.DomainException;

/** Raised when registering a user whose username is already in use. */
public class DuplicateUsernameException extends DomainException {
    public DuplicateUsernameException(String username) {
        super(IamErrorCode.DUPLICATE_USERNAME, "A user with username %s already exists".formatted(username));
    }
}
