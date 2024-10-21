package com.uade.demo.exceptions;

public class InvalidItemCountException extends RuntimeException {
    public InvalidItemCountException(String message) {
        super(message);
    }
}
