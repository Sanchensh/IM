package com.mrxu.common.exception;

/**
 * @Description: 未知的command异常
 * @author: ztowh
 * @Date: 2018-12-29 14:20
 */
public class UnknowCommandException extends RuntimeException {

    public UnknowCommandException(String errorMsg) {
        super(errorMsg);
    }
}
