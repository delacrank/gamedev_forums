package com.juan.gamedevforums.web.error;

public final class CategoriesNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5861310537366287163L;

    public CategoriesNotFoundException() {
        super();
    }

    public CategoriesNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CategoriesNotFoundException(final String message) {
        super(message);
    }

    public CategoriesNotFoundException(final Throwable cause) {
        super(cause);
    }

}
