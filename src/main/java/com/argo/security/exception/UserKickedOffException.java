package com.argo.security.exception;

/**
 * Created by yamingd on 9/9/15.
 */
public class UserKickedOffException extends Exception {

    private int statusCode = 60900;

    public UserKickedOffException(String message) {
        super(message);
    }

    public UserKickedOffException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserKickedOffException(Throwable cause) {
        super(cause);
    }

    public UserKickedOffException() {
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
