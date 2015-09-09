package com.argo.security.exception;

/**
 * Created by yamingd on 9/9/15.
 */
public class CookieInvalidException extends Exception {

    private int statusCode = 498;

    public CookieInvalidException() {
    }

    public CookieInvalidException(String message) {
        super(message);
    }

    public CookieInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public CookieInvalidException(Throwable cause) {
        super(cause);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
