package com.mrxu.common.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BusinessException extends RuntimeException {

    private String code;
    private String message;
    private Throwable throwable;

    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}