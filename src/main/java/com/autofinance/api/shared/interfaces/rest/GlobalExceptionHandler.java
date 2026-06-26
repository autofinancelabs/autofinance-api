package com.autofinance.api.shared.interfaces.rest;

import com.autofinance.api.shared.domain.exceptions.DomainException;
import com.autofinance.api.shared.domain.exceptions.ErrorCode;
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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Translates every failure to an RFC 9457 {@code ProblemDetail} in one place, tagged with a stable
 * {@code code} (the frontend reacts to the code, not the message). Application-wide: a single
 * {@code @ExceptionHandler(DomainException.class)} serves every bounded context — each domain exception
 * carries its own {@link ErrorCode} (code + HTTP-agnostic category), so adding one needs no change here.
 * Spring MVC exceptions are mapped by overriding the base handler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // --- Domain exceptions (any context) -----------------------------------

    @ExceptionHandler(DomainException.class)
    ProblemDetail handle(DomainException ex, HttpServletRequest request) {
        log.debug("Domain error {} at {}: {}", ex.errorCode().code(), request.getRequestURI(), ex.getMessage());
        return problem(ex.errorCode(), ex.getMessage(), request);
    }

    /** Value-object validation, enum parsing, etc. (not a {@link DomainException}). */
    @ExceptionHandler(IllegalArgumentException.class)
    ProblemDetail handle(IllegalArgumentException ex, HttpServletRequest request) {
        log.debug("Validation error at {}: {}", request.getRequestURI(), ex.getMessage());
        return problem(WebErrorCode.VALIDATION_FAILED, ex.getMessage(), request);
    }

    /** Last-resort fallback: anything unmapped becomes a 500 without leaking internals. */
    @ExceptionHandler(Exception.class)
    ProblemDetail handleUnexpected(Exception ex, HttpServletRequest request) {
        log.error("Unhandled error at {} {}", request.getMethod(), request.getRequestURI(), ex);
        return problem(WebErrorCode.INTERNAL_ERROR, "An unexpected error occurred.", request);
    }

    private ProblemDetail problem(ErrorCode code, String detail, HttpServletRequest request) {
        return ProblemDetails.of(code, detail, request.getRequestURI());
    }

    // --- Spring MVC exceptions (override the base, then tag with a code) ----

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        ResponseEntity<Object> response = super.handleMethodArgumentNotValid(ex, headers, status, request);
        if (response != null && response.getBody() instanceof ProblemDetail problem) {
            List<Map<String, String>> errors = ex.getBindingResult().getFieldErrors().stream()
                    .sorted(Comparator.comparing(FieldError::getField))
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
                problem.setType(ProblemDetails.typeFor(code));
            }
            problem.setProperty("timestamp", Instant.now().truncatedTo(ChronoUnit.MILLIS));
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
            return WebErrorCode.MISSING_TENANT;
        }
        if (ex instanceof MethodArgumentNotValidException) {
            return WebErrorCode.VALIDATION_FAILED;
        }
        if (ex instanceof HttpMessageNotReadableException || ex instanceof TypeMismatchException) {
            return WebErrorCode.MALFORMED_REQUEST;
        }
        return null; // unknown framework error: keep the parent's status, no catalog code
    }
}
