package com.ercan.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ValidationError {
    private String fieldName;
    private Object value;
    private String message;
}
