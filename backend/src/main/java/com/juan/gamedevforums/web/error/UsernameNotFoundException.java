package com.juan.gamedevforums.web.error;

public final class UsernameNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public UsernameNotFoundException() {
        super();
    }

    public UsernameNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UsernameNotFoundException(final String message) {
        super(message);
    }

    public UsernameNotFoundException(final Throwable cause) {
        super(cause);
    }

}
