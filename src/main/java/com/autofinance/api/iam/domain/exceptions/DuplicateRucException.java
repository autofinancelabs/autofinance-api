package com.autofinance.api.iam.domain.exceptions;

import com.autofinance.api.iam.domain.model.valueobjects.Ruc;
import com.autofinance.api.shared.domain.exceptions.DomainException;

/** Raised when registering a dealership whose RUC is already in use. */
public class DuplicateRucException extends DomainException {
    public DuplicateRucException(Ruc ruc) {
        super(IamErrorCode.DUPLICATE_RUC, "A dealership with RUC %s already exists".formatted(ruc.value()));
    }
}
