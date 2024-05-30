package com.example.ebanking.util;

import com.example.ebanking.database.entity.AbstractSerializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class RestApiResponse<T> extends AbstractSerializable {
    private Instant timestamp;
    @Schema(example = "200")
    private int status;
    private T response;
    private String error;
    private String message;
    @Schema(hidden = true)
    private List<FieldError> errors;

    public static <T> RestApiResponse<T> of(Instant timestamp, int status, T response, String error, String message, List<FieldError> errors) {
        RestApiResponse<T> restApiResponse = new RestApiResponse<>();
        restApiResponse.setTimestamp(timestamp);
        restApiResponse.setStatus(status);
        restApiResponse.setResponse(response);
        restApiResponse.setError(error);
        restApiResponse.setMessage(message);
        restApiResponse.setErrors(errors);
        return restApiResponse;
    }

    public static <T> RestApiResponse<T> of(T response, HttpStatus status) {
        return RestApiResponse.of(Instant.now(), status.value(), response, null, null, null);
    }

    public static <T> RestApiResponse<T> ok() {
        return ok(null);
    }

    public static <T> RestApiResponse<T> ok(T response) {
        return of(response, HttpStatus.OK);
    }

    public static RestApiResponse<?> error(String errorMessage, HttpStatus status) {
        return error(errorMessage, status, null);
    }

    public static RestApiResponse<?> error(String errorMessage, HttpStatus status, List<FieldError> errors) {
        return RestApiResponse.of(Instant.now(), status.value(), null, status.getReasonPhrase(), errorMessage, errors);
    }

    @Getter
    @Setter
    public static class FieldError {
        private String field;
        private String value;
        private String message;
    }
}
