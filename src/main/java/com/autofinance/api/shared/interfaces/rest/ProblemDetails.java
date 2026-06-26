package com.autofinance.api.shared.interfaces.rest;

import com.autofinance.api.shared.domain.exceptions.ErrorCategory;
import com.autofinance.api.shared.domain.exceptions.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Builds RFC 9457 {@link ProblemDetail} responses with our conventions (stable {@code code}, {@code type}
 * URI, ms-precision {@code timestamp}). Shared by the MVC advice ({@link GlobalExceptionHandler}) and the
 * Spring Security entry points (which run outside MVC), so the error shape is identical everywhere.
 */
public final class ProblemDetails {

    private static final String TYPE_BASE = "https://api.autofinance/errors/";

    private ProblemDetails() {
    }

    /** Maps an HTTP-agnostic {@link ErrorCategory} to its HTTP status. */
    public static HttpStatus statusFor(ErrorCategory category) {
        return switch (category) {
            case VALIDATION -> HttpStatus.BAD_REQUEST;
            case UNPROCESSABLE -> HttpStatus.UNPROCESSABLE_ENTITY;
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case CONFLICT -> HttpStatus.CONFLICT;
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case FORBIDDEN -> HttpStatus.FORBIDDEN;
            case INTERNAL -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }

    /** Stable per-code {@code type} URI (e.g. {@code .../errors/invalid-credentials}). */
    public static URI typeFor(ErrorCode code) {
        return URI.create(TYPE_BASE + code.code().toLowerCase().replace('_', '-'));
    }

    /** Full ProblemDetail for an error code at a request path. */
    public static ProblemDetail of(ErrorCode code, String detail, String instancePath) {
        HttpStatus status = statusFor(code.category());
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(status, detail);
        problem.setTitle(status.getReasonPhrase());
        problem.setType(typeFor(code));
        problem.setInstance(URI.create(instancePath));
        problem.setProperty("code", code.code());
        problem.setProperty("timestamp", Instant.now().truncatedTo(ChronoUnit.MILLIS));
        return problem;
    }

    /**
     * The same RFC 9457 fields as {@link #of} as a flat map, for callers that serialize outside Spring MVC
     * (e.g. the Spring Security entry points, which run before the MVC ProblemDetail support).
     */
    public static Map<String, Object> body(ErrorCode code, String detail, String instancePath) {
        HttpStatus status = statusFor(code.category());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("type", typeFor(code).toString());
        body.put("title", status.getReasonPhrase());
        body.put("status", status.value());
        body.put("detail", detail);
        body.put("instance", instancePath);
        body.put("code", code.code());
        body.put("timestamp", Instant.now().truncatedTo(ChronoUnit.MILLIS).toString());
        return body;
    }
}
