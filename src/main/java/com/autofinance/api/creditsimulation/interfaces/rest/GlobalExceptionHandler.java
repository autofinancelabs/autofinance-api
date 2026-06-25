package com.autofinance.api.creditsimulation.interfaces.rest;

import com.autofinance.api.creditsimulation.domain.exceptions.CurrencyMismatchException;
import com.autofinance.api.creditsimulation.domain.exceptions.InvalidSimulationConfigurationException;
import com.autofinance.api.creditsimulation.domain.exceptions.IrrNotBracketedException;
import com.autofinance.api.creditsimulation.domain.exceptions.MissingCapitalizationException;
import com.autofinance.api.creditsimulation.domain.exceptions.PercentageOutOfRangeException;
import com.autofinance.api.creditsimulation.domain.exceptions.ScheduleNotBalancedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Translates domain failures to HTTP at the edge, in one place — the domain exceptions stay free of
 * web concerns. Bean-validation and missing/malformed header errors are handled by Spring Boot's
 * defaults (also 400).
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Invalid configuration / value-object validation (incl. enum parsing) → 400. */
    @ExceptionHandler({
            InvalidSimulationConfigurationException.class,
            CurrencyMismatchException.class,
            PercentageOutOfRangeException.class,
            MissingCapitalizationException.class,
            IllegalArgumentException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequest(RuntimeException ex) {
        return ErrorResponse.create(ex, HttpStatusCode.valueOf(400), ex.getMessage());
    }

    /** Valid request, but the schedule/indicators could not be computed coherently → 422. */
    @ExceptionHandler({
            ScheduleNotBalancedException.class,
            IrrNotBracketedException.class
    })
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleUnprocessable(RuntimeException ex) {
        return ErrorResponse.create(ex, HttpStatusCode.valueOf(422), ex.getMessage());
    }
}
