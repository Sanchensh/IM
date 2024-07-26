package com.mrxu.common.exception;

public class SerializationException extends Exception {
    /**
     * For serialization
     */
    private static final long serialVersionUID = 5668965722686668067L;

    /**
     * Constructor.
     */
    public SerializationException() {

    }

    /**
     * Constructor.
     */
    public SerializationException(String message) {
        super(message);
    }

    /**
     * Constructor.
     */
    public SerializationException(String message, Throwable cause) {
        super(message, cause);
    }

}