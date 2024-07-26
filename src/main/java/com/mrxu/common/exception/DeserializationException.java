package com.mrxu.common.exception;

public class DeserializationException extends Exception {
    /**
     * For serialization
     */
    private static final long serialVersionUID = 310446237157256052L;

    private boolean serverSide = false;

    /**
     * Constructor.
     */
    public DeserializationException() {

    }

    /**
     * Constructor.
     */
    public DeserializationException(String message) {
        super(message);
    }


    /**
     * Constructor.
     */
    public DeserializationException(String message, Throwable cause) {
        super(message, cause);
    }

}