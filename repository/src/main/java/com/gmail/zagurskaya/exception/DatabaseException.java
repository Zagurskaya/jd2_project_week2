package com.gmail.zagurskaya.exception;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String message) {
        super(message);
    }

    public DatabaseException(String message, Throwable e) {
        super(message, e);
    }
}
