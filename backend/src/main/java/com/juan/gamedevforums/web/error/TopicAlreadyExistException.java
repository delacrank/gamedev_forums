package com.juan.gamedevforums.web.error;

public final class TopicAlreadyExistException extends RuntimeException {

    private static final long serialVersionUID = 5821310537366287163L;

    public TopicAlreadyExistException() {
        super();
    }

    public TopicAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TopicAlreadyExistException(final String message) {
        super(message);
    }

    public TopicAlreadyExistException(final Throwable cause) {
        super(cause);
    }

}
