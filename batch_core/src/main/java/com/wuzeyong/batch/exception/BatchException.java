package com.wuzeyong.batch.exception;

/**
 * @author WUZEYONG
 */
public class BatchException extends RuntimeException {

    private static final long serialVersionUID = -1343739516839252250L;

    public BatchException(final String errorMessage, final Object... args) {
        super(String.format(errorMessage, args));
    }

    public BatchException(final String message, final Exception cause) {
        super(message, cause);
    }

    public BatchException(final Exception cause) {
        super(cause);
    }
}
