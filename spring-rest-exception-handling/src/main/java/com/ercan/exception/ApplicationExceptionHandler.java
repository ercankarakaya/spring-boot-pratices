package com.ercan.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<?> handleApplicationException(ApplicationException exception, HttpServletRequest request) {
        var guid = UUID.randomUUID().toString();
        log.error(String.format("Error GUID=%s; Error Message: %s", guid, exception.getMessage()), exception);

        var response = ApiErrorResponse
                .builder()
                .guid(guid)
                .errorCode(exception.getErrorCode())
                .message(exception.getMessage())
                .statusCode(exception.getHttpStatus().value())
                .statusName(exception.getHttpStatus().name())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, exception.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleUnknownException(Exception exception, HttpServletRequest request) {
        var guid = UUID.randomUUID().toString();
        log.error(String.format("Error GUID=%s; Error Message: %s", guid, exception.getMessage()), exception);

        var INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
        var response = ApiErrorResponse
                .builder()
                .guid(guid)
                .errorCode(INTERNAL_SERVER_ERROR.name())
                .message(INTERNAL_SERVER_ERROR.getReasonPhrase())
                .statusCode(INTERNAL_SERVER_ERROR.value())
                .statusName(INTERNAL_SERVER_ERROR.name())
                .path(request.getRequestURI())
                .method(request.getMethod())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response,INTERNAL_SERVER_ERROR);
    }
}
