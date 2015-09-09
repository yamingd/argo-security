package com.argo.security.exception;

/**
 * Created by yamingd on 9/9/15.
 */
public class PasswordInvalidException extends Exception {

    private int statusCode = 498;

    public PasswordInvalidException() {
    }

    public PasswordInvalidException(String message) {
        super(message);
    }

    public PasswordInvalidException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordInvalidException(Throwable cause) {
        super(cause);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
