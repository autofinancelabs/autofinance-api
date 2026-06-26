package com.autofinance.api.creditsimulation.domain.exceptions;

import com.autofinance.api.shared.domain.exceptions.DomainException;

/** Raised when the IRR bisection cannot bracket a root in [0, 1] (NPV has the same sign at both ends). */
public class IrrNotBracketedException extends DomainException {
    public IrrNotBracketedException() {
        super(SimulationErrorCode.IRR_NOT_BRACKETED,
                "Cannot bracket an IRR root in [0, 1]: the cash flows are not conventional");
    }
}
