package com.autofinance.api.iam.domain.exceptions;

import com.autofinance.api.shared.domain.exceptions.DomainException;
import com.autofinance.api.shared.domain.model.valueobjects.Email;

/** Raised when registering a user whose email is already in use. */
public class DuplicateEmailException extends DomainException {
    public DuplicateEmailException(Email email) {
        super(IamErrorCode.DUPLICATE_EMAIL, "A user with email %s already exists".formatted(email.email()));
    }
}
