package com.juan.gamedevforums.web.error;

public final class UsernameAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public UsernameAlreadyExistException() {
        super();
    }

    public UsernameAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UsernameAlreadyExistException(final String message) {
        super(message);
    }

    public UsernameAlreadyExistException(final Throwable cause) {
        super(cause);
    }

}
