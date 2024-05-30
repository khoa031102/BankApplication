package com.example.ebanking.util;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RestApiException extends RuntimeException {
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    private Object[] params;

    public RestApiException() {
    }

    public RestApiException(String message) {
        super(message);
    }

    public RestApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestApiException(Throwable cause) {
        super(cause);
    }

    public RestApiException setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public RestApiException setParams(Object... params) {
        this.params = params;
        return this;
    }

    public static RestApiException unauthorized(String message, Object... params) {
        return unauthorized(message).setParams(params);
    }

    public static RestApiException unauthorized(String message) {
        return new RestApiException(message).setStatus(HttpStatus.UNAUTHORIZED);
    }

    public static RestApiException badRequest(String message, Object... params) {
        return badRequest(message).setParams(params);
    }

    public static RestApiException badRequest(String message) {
        return new RestApiException(message).setStatus(HttpStatus.BAD_REQUEST);
    }

    public static RestApiException forbidden(String message, Object... params) {
        return forbidden(message).setParams(params);
    }

    public static RestApiException forbidden(String message) {
        return new RestApiException(message).setStatus(HttpStatus.FORBIDDEN);
    }
}
