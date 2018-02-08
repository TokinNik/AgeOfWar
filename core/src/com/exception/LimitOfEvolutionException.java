package com.exception;

public class LimitOfEvolutionException extends Exception {

    public LimitOfEvolutionException() {
        super("Can't evolve any more");
    }
}
