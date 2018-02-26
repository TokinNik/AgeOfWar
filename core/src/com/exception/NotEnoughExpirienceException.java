package com.exception;

/**
 * Created by denis on 26.02.18.
 */

public class NotEnoughExpirienceException extends Exception {

    public NotEnoughExpirienceException() {
        super("Not enough expirience for perform operation");
    }
}
