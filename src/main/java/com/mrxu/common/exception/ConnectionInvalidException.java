package com.mrxu.common.exception;


public class ConnectionInvalidException extends BusinessException {

    private static final long serialVersionUID = 1415831886623630165L;

    /**
     * Constructor.
     */
    public ConnectionInvalidException(String message) {
        super("0000", message);
    }

    /**
     * Constructor.
     */
    public ConnectionInvalidException(String message, Throwable cause) {
        super("0000", message, cause);
    }

}