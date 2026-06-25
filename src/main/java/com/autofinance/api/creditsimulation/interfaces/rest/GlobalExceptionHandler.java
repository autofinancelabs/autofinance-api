package com.autofinance.api.creditsimulation.interfaces.rest;

import com.autofinance.api.creditsimulation.domain.exceptions.CurrencyMismatchException;
import com.autofinance.api.creditsimulation.domain.exceptions.InvalidSimulationConfigurationException;
import com.autofinance.api.creditsimulation.domain.exceptions.IrrNotBracketedException;
import com.autofinance.api.creditsimulation.domain.exceptions.MissingCapitalizationException;
import com.autofinance.api.creditsimulation.domain.exceptions.PercentageOutOfRangeException;
import com.autofinance.api.creditsimulation.domain.exceptions.ScheduleNotBalancedException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Translates every failure to an RFC 9457 {@code ProblemDetail} in one place, tagged with a stable
 * {@code code} from the {@link ErrorCode} catalog (the frontend reacts to the code, not the message).
 * Domain exceptions stay free of HTTP; Spring MVC exceptions are mapped by overriding the base handler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // --- Domain exceptions -------------------------------------------------

    @ExceptionHandler(InvalidSimulationConfigurationException.class)
    ProblemDetail handle(InvalidSimulationConfigurationException ex, HttpServletRequest request) {
        return domainProblem(ErrorCode.INVALID_SIMULATION_CONFIGURATION, ex, request);
    }

    @ExceptionHandler(PercentageOutOfRangeException.class)
    ProblemDetail handle(PercentageOutOfRangeException ex, HttpServletRequest request) {
        return domainProblem(ErrorCode.PERCENTAGE_OUT_OF_RANGE, ex, request);
    }

    @ExceptionHandler(CurrencyMismatchException.class)
    ProblemDetail handle(CurrencyMismatchException ex, HttpServletRequest request) {
        return domainProblem(ErrorCode.CURRENCY_MISMATCH, ex, request);
    }

    @ExceptionHandler(MissingCapitalizationException.class)
    ProblemDetail handle(MissingCapitalizationException ex, HttpServletRequest request) {
        return domainProblem(ErrorCode.MISSING_CAPITALIZATION, ex, request);
    }

    /** Value-object validation, enum parsing, etc. */
    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail handle(IllegalArgumentException ex, HttpServletRequest request) {
        return domainProblem(ErrorCode.VALIDATION_FAILED, ex, request);
    }

    @ExceptionHandler(ScheduleNotBalancedException.class)
    ProblemDetail handle(ScheduleNotBalancedException ex, HttpServletRequest request) {
        return domainProblem(ErrorCode.SCHEDULE_NOT_BALANCED, ex, request);
    }

    @ExceptionHandler(IrrNotBracketedException.class)
    ProblemDetail handle(IrrNotBracketedException ex, HttpServletRequest request) {
        return domainProblem(ErrorCode.IRR_NOT_BRACKETED, ex, request);
    }

    /** Last-resort fallback: anything unmapped becomes a 500 without leaking internals. */
    @ExceptionHandler(Exception.class)
    ProblemDetail handleUnexpected(Exception ex, HttpServletRequest request) {
        log.error("Unhandled error at {} {}", request.getMethod(), request.getRequestURI(), ex);
        return problem(ErrorCode.INTERNAL_ERROR, "An unexpected error occurred.", request);
    }

    private ProblemDetail domainProblem(ErrorCode code, Exception ex, HttpServletRequest request) {
        log.debug("Domain error {} at {}: {}", code.code(), request.getRequestURI(), ex.getMessage());
        return problem(code, ex.getMessage(), request);
    }

    private ProblemDetail problem(ErrorCode code, String detail, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(code.status(), detail);
        problem.setTitle(code.status().getReasonPhrase());
        problem.setType(code.type());
        problem.setInstance(URI.create(request.getRequestURI()));
        problem.setProperty("code", code.code());
        problem.setProperty("timestamp", Instant.now());
        return problem;
    }

    // --- Spring MVC exceptions (override the base, then tag with a code) ----

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        ResponseEntity<Object> response = super.handleMethodArgumentNotValid(ex, headers, status, request);
        if (response != null && response.getBody() instanceof ProblemDetail problem) {
            List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                    .map(fe -> Map.of("field", fe.getField(),
                            "message", fe.getDefaultMessage() == null ? "" : fe.getDefaultMessage()))
                    .toList();
            problem.setProperty("errors", errors);
        }
        return response;
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body,
                                                             HttpHeaders headers, HttpStatusCode statusCode,
                                                             WebRequest request) {
        ResponseEntity<Object> response = super.handleExceptionInternal(ex, body, headers, statusCode, request);
        if (response != null && response.getBody() instanceof ProblemDetail problem) {
            ErrorCode code = codeFor(ex);
            if (code != null) {
                problem.setProperty("code", code.code());
                problem.setType(code.type());
            }
            problem.setProperty("timestamp", Instant.now());
        }
        if (statusCode.is5xxServerError()) {
            log.error("Framework error ({}) at {}", statusCode, request.getDescription(false), ex);
        } else {
            log.debug("Framework error ({}) at {}: {}", statusCode, request.getDescription(false), ex.getMessage());
        }
        return response;
    }

    @Nullable
    private ErrorCode codeFor(Exception ex) {
        if (ex instanceof MissingRequestHeaderException) {
            return ErrorCode.MISSING_TENANT;
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return ErrorCode.VALIDATION_FAILED;
        }
        if (ex instanceof HttpMessageNotReadableException || ex instanceof TypeMismatchException) {
            return ErrorCode.MALFORMED_REQUEST;
        }
        return null; // unknown framework error: keep the parent's status, no catalog code
    }
}
