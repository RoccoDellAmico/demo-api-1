package com.uade.demo.controllers;

public class YourCustomException extends RuntimeException {
    public YourCustomException(String message) {
        super(message);
    }
}
