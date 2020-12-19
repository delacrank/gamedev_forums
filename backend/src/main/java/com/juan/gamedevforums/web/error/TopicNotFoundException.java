package com.juan.gamedevforums.web.error;

public final class TopicNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5861330537326287163L;

    public TopicNotFoundException() {
        super();
    }

    public TopicNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TopicNotFoundException(final String message) {
        super(message);
    }

    public TopicNotFoundException(final Throwable cause) {
        super(cause);
    }

}
