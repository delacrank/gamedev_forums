package com.juan.gamedevforums.web.error;

public final class PostNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5861330537326287163L;

    public PostNotFoundException() {
        super();
    }

    public PostNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PostNotFoundException(final String message) {
        super(message);
    }

    public PostNotFoundException(final Throwable cause) {
        super(cause);
    }

}
