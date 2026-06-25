package com.autofinance.api.creditsimulation.domain.exceptions;

/** Raised when the IRR bisection cannot bracket a root in [0, 1] (NPV has the same sign at both ends). */
public class IrrNotBracketedException extends RuntimeException {
    public IrrNotBracketedException() {
        super("Cannot bracket an IRR root in [0, 1]: the cash flows are not conventional");
    }
}
