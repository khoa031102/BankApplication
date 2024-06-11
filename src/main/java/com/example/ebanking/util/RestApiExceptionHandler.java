package com.example.ebanking.util;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.LocaleResolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Slf4j
@RestControllerAdvice
public final class RestApiExceptionHandler /*extends ResponseEntityExceptionHandler*/ {
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    @Getter
    private final ThreadLocal<RestApiException> exceptionThread = new ThreadLocal<>();

    public RestApiExceptionHandler(MessageSource messageSource, LocaleResolver localeResolver) {
        this.messageSource = messageSource;
        this.localeResolver = localeResolver;
    }

    @ExceptionHandler(TypeMismatchException.class)
    public ResponseEntity<RestApiResponse<?>> handleRestApiException(TypeMismatchException ex, Locale locale) {
        String errorMessage = messageSource.getMessage("Exception.TypeMismatch", new String[]{ex.getPropertyName()}, locale);
        RestApiResponse<?> response = RestApiResponse.error(errorMessage, HttpStatus.BAD_REQUEST);
        log.error("{}: {}", ex.getClass().getSimpleName(), response.toJson());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<RestApiResponse<?>> handleRestApiException(HttpMessageNotReadableException ex, Locale locale) {
        String errorMessage = messageSource.getMessage("Exception.InvalidDataFormat", new String[]{ex.getMessage()}, locale);
        RestApiResponse<?> response = RestApiResponse.error(errorMessage, HttpStatus.BAD_REQUEST);
        log.error("{}: {}", ex.getClass().getSimpleName(), response.toJson());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(MissingRequestValueException.class)
    public ResponseEntity<RestApiResponse<?>> handleRestApiException(MissingRequestValueException ex, Locale locale) {
        String[] params = null;
        if (ex instanceof MissingServletRequestParameterException) {
            params = new String[]{((MissingServletRequestParameterException) ex).getParameterName()};
        }
        //String defaultMessage = StringUtils.hasText(ex.getBody().getDetail()) ? ex.getBody().getDetail() : ex.getMessage();
        String errorMessage = messageSource.getMessage("Exception.MissingRequestValue", params, locale);
        RestApiResponse<?> response = RestApiResponse.error(errorMessage, HttpStatus.BAD_REQUEST);
        log.error("{}: {}", ex.getClass().getSimpleName(), response.toJson());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<RestApiResponse<?>> handleRestApiException(BindException ex, Locale locale) {
        String errorMessage = messageSource.getMessage("Exception.InvalidRequestContent", null, locale);
        List<FieldError> fieldErrors = ex.getFieldErrors();
        List<RestApiResponse.FieldError> errors = null;
        if (!fieldErrors.isEmpty()) {
            errors = new ArrayList<>();
            for (FieldError fieldError : fieldErrors) {
                RestApiResponse.FieldError error = new RestApiResponse.FieldError();
                error.setField(fieldError.getField());
                error.setValue(String.valueOf(fieldError.getRejectedValue()));
                error.setMessage(messageSource.getMessage(fieldError, locale));
                errors.add(error);
            }
        }
        RestApiResponse<?> response = RestApiResponse.error(errorMessage, HttpStatus.BAD_REQUEST, errors);
        log.error("{}: {}", ex.getClass().getSimpleName(), response.toJson());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(RestApiException.class)
    public ResponseEntity<RestApiResponse<?>> handleRestApiException(RestApiException ex, Locale locale) {
        String errorMessage = messageSource.getMessage(ex.getMessage(), ex.getParams(), locale);
        RestApiResponse<?> response = RestApiResponse.error(errorMessage, ex.getStatus());
        log.error("{}: {}", ex.getClass().getSimpleName(), response.toJson());
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RestApiResponse<?>> handleException(Exception ex, HttpServletRequest request, Locale locale) {
        RestApiException restApiException = exceptionThread.get();
        if (restApiException != null) {
            Locale resolvedLocale = localeResolver.resolveLocale(request);
            return handleRestApiException(restApiException, resolvedLocale);
        }

        String errorMessage = messageSource.getMessage(ex.getMessage(), null, locale);
        HttpStatus httpStatus;
        if (ex instanceof AccessDeniedException)
            httpStatus = HttpStatus.FORBIDDEN;
        else if (ex instanceof AuthenticationException)
            httpStatus = HttpStatus.UNAUTHORIZED;
        else
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

        RestApiResponse<?> response = RestApiResponse.error(errorMessage, httpStatus);
        if (httpStatus == HttpStatus.INTERNAL_SERVER_ERROR) {
            log.error("INTERNAL_SERVER_ERROR:", ex);
        } else {
            log.error("{}: {}", ex.getClass().getSimpleName(), response.toJson());
        }
        return ResponseEntity.ok(response);
    }

    @PostConstruct
    private void onPostConstruct() {
        log.info("Initialized RestExceptionHandler");
    }

}
