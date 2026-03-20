package com.juanroy.secondbrain.shared.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ProblemDetail> handleAppException(AppException ex, HttpServletRequest request) {
        ErrorCode errorCode = ex.getErrorCode();
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(errorCode.getHttpStatus(), errorCode.getMessage());
        detail.setTitle(errorCode.name());
        detail.setType(URI.create("https://secondbrain.dev/problems/" + errorCode.name().toLowerCase()));
        detail.setInstance(URI.create(request.getRequestURI()));
        detail.setProperty("errorCode", errorCode.getCode());
        return ResponseEntity.status(errorCode.getHttpStatus()).body(detail);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String errors = ex.getBindingResult().getFieldErrors().stream()
                .map(this::formatFieldError)
                .collect(Collectors.joining(", "));

        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed: " + errors);
        detail.setTitle(ErrorCode.VALIDATION_FAILED.name());
        detail.setType(URI.create("https://secondbrain.dev/problems/validation-failed"));
        detail.setInstance(URI.create(request.getRequestURI()));
        detail.setProperty("errorCode", ErrorCode.VALIDATION_FAILED.getCode());
        return ResponseEntity.badRequest().body(detail);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest request) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Validation failed: " + ex.getMessage());
        detail.setTitle(ErrorCode.VALIDATION_FAILED.name());
        detail.setType(URI.create("https://secondbrain.dev/problems/validation-failed"));
        detail.setInstance(URI.create(request.getRequestURI()));
        detail.setProperty("errorCode", ErrorCode.VALIDATION_FAILED.getCode());
        return ResponseEntity.badRequest().body(detail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleGeneric(Exception ex, HttpServletRequest request) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        detail.setTitle("INTERNAL_SERVER_ERROR");
        detail.setType(URI.create("https://secondbrain.dev/problems/internal-server-error"));
        detail.setInstance(URI.create(request.getRequestURI()));
        detail.setProperty("errorCode", 9999);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(detail);
    }

    private String formatFieldError(FieldError error) {
        String defaultMessage = error.getDefaultMessage() == null ? "invalid value" : error.getDefaultMessage();
        return error.getField() + " " + defaultMessage;
    }
}
