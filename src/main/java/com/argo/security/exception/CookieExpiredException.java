package com.argo.security.exception;

/**
 * Created by yamingd on 9/9/15.
 */
public class CookieExpiredException extends Exception {

    public CookieExpiredException() {
    }

    public CookieExpiredException(String message) {
        super(message);
    }

    public CookieExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public CookieExpiredException(Throwable cause) {
        super(cause);
    }
}
