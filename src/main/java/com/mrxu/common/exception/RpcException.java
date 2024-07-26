package com.mrxu.common.exception;

public class RpcException extends RuntimeException {

    /**
     * Constructor.
     */
    public RpcException() {

    }

    /**
     * Constructor.
     */
    public RpcException(String message) {
        super(message);
    }

    /**
     * Constructor.
     */
    public RpcException(String message, Throwable cause) {
        super(message, cause);
    }
}
