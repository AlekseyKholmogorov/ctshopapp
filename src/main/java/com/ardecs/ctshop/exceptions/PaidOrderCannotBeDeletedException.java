package com.ardecs.ctshop.exceptions;

public class PaidOrderCannotBeDeletedException extends RuntimeException {
    public PaidOrderCannotBeDeletedException(String message) {
        super(message);
    }
}
