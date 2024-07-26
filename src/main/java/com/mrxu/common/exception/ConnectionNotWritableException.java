package com.mrxu.common.exception;

/**
 * @Description:
 * @author: ztowh
 * @Date: 2019-01-16 15:01
 */
public class ConnectionNotWritableException extends RuntimeException {

    private static final long serialVersionUID = -8222518418937630309L;

    /**
     * Constructor.
     */
    public ConnectionNotWritableException() {

    }

    /**
     * Constructor.
     */
    public ConnectionNotWritableException(String message) {
        super(message);
    }

    /**
     * Constructor.
     */
    public ConnectionNotWritableException(String message, Throwable cause) {
        super(message, cause);
    }

}
