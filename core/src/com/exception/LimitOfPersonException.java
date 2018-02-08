package com.exception;

public class LimitOfPersonException extends Exception {

    public LimitOfPersonException() {
        super("Can't create any more");
    }
}
