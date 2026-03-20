package com.juanroy.secondbrain.shared.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_ALREADY_EXISTS(1001, "User already exists", HttpStatus.CONFLICT),
    INVALID_CREDENTIALS(1002, "Invalid username or password", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_INVALID(1003, "Refresh token is invalid", HttpStatus.UNAUTHORIZED),
    REFRESH_TOKEN_EXPIRED(1004, "Refresh token is expired or revoked", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1005, "Unauthorized", HttpStatus.UNAUTHORIZED),
    USER_NOT_FOUND(2001, "User not found", HttpStatus.NOT_FOUND),
    NOTE_NOT_FOUND(2002, "Note not found", HttpStatus.NOT_FOUND),
    WEAK_PASSWORD(2003, "Password does not meet strength requirements", HttpStatus.BAD_REQUEST),
    AI_API_FAILURE(4001, "AI API failed", HttpStatus.BAD_GATEWAY),
    VALIDATION_FAILED(3001, "Validation failed", HttpStatus.BAD_REQUEST);


    private final String message;
    private final int code;
    private final HttpStatus httpStatus;

    ErrorCode(int code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}

