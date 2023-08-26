package com.ercan.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiErrorResponse {
    private String guid;
    private String errorCode;
    private String message;
    private Integer statusCode;
    private String statusName;
    private String path;
    private String method;
    private LocalDateTime timestamp;
}
