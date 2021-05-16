package com.juan.gamedevforums.web.error;

public final class StockNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 5861330537326287163L;

    public StockNotFoundException() {
        super();
    }

    public StockNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public StockNotFoundException(final String message) {
        super(message);
    }

    public StockNotFoundException(final Throwable cause) {
        super(cause);
    }

}
