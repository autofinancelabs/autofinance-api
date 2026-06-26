package com.autofinance.api.iam.domain.exceptions;

import com.autofinance.api.shared.domain.exceptions.DomainException;

/** Raised when sign-in fails: unknown identifier or wrong password. Deliberately does not disclose which. */
public class InvalidCredentialsException extends DomainException {
    public InvalidCredentialsException() {
        super(IamErrorCode.INVALID_CREDENTIALS, "Invalid credentials");
    }
}
