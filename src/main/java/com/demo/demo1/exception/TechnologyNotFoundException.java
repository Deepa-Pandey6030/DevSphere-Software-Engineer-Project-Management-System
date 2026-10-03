package com.demo.demo1.exception;

public class TechnologyNotFoundException extends RuntimeException {

    public TechnologyNotFoundException(String message) {
        super(message);
    }
}