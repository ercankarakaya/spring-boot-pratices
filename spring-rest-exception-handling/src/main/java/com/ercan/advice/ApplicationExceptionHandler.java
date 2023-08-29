package com.ercan.advice;

import com.ercan.exception.ApplicationException;
import com.ercan.response.ApiErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
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

        return buildResponseEntity(exception, exception.getHttpStatus(), request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception, HttpServletRequest request) {

        return buildResponseEntity(exception, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(javax.validation.ConstraintViolationException exception,WebRequest request){
        ApiErrorResponse response = (ApiErrorResponse) buildResponseEntity(exception, HttpStatus.BAD_REQUEST, request).getBody();
        response.addValidationErrors(exception.getConstraintViolations());
        return buildResponseEntity(exception,HttpStatus.BAD_REQUEST,request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return (ResponseEntity<Object>) buildResponseEntity(exception, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

        return (ResponseEntity<Object>) buildResponseEntity(exception, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return (ResponseEntity<Object>) buildResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ApiErrorResponse response = (ApiErrorResponse) buildResponseEntity(exception, status, request).getBody();
        response.addValidationErrors(exception.getBindingResult().getFieldErrors());
        response.addValidationError(exception.getBindingResult().getGlobalErrors());
        return buildResponseEntity(response,status);
    }

    private ResponseEntity<Object> buildResponseEntity(ApiErrorResponse response, HttpStatus status){
        return new ResponseEntity<>(response,status);
    }

    private ResponseEntity<?> buildResponseEntity(Exception exception, HttpStatus status, Object request) {
        var guid = UUID.randomUUID().toString();
        log.error(String.format("Error GUID=%s; Error Message: %s", guid, exception.getMessage()), exception);

        var response = new ApiErrorResponse();
        response.setGuid(guid);
        response.setMessage(exception.getMessage());
        response.setTimestamp(LocalDateTime.now());
        response.setStatusCode(status.value());
        response.setStatusName(status.name());

        if (exception instanceof ApplicationException) {
            response.setErrorCode(((ApplicationException) exception).getErrorCode());
        }
        if (request instanceof HttpServletRequest) {
            response.setPath(((HttpServletRequest) request).getRequestURI());
            response.setMethod(((HttpServletRequest) request).getMethod());
        } else if (request instanceof WebRequest) {
            response.setPath(((ServletWebRequest) request).getRequest().getRequestURI());
            response.setMethod(((ServletWebRequest) request).getRequest().getMethod());
        }

        return buildResponseEntity(response,status);
    }


//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleUnknownException(Exception exception, HttpServletRequest request) {
//        var guid = UUID.randomUUID().toString();
//        log.error(String.format("Error GUID=%s; Error Message: %s", guid, exception.getMessage()), exception);
//
//        var INTERNAL_SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;
//        var response = ApiErrorResponse
//                .builder()
//                .guid(guid)
//                .errorCode(INTERNAL_SERVER_ERROR.name())
//                .message(INTERNAL_SERVER_ERROR.getReasonPhrase())
//                .statusCode(INTERNAL_SERVER_ERROR.value())
//                .statusName(INTERNAL_SERVER_ERROR.name())
//                .path(request.getRequestURI())
//                .method(request.getMethod())
//                .timestamp(LocalDateTime.now())
//                .build();
//
//        return buildResponseEntity(response);
//    }

}
