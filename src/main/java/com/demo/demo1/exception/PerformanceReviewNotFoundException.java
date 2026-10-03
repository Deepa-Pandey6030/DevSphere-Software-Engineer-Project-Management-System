package com.demo.demo1.exception;

public class PerformanceReviewNotFoundException extends RuntimeException {

    public PerformanceReviewNotFoundException(String message) {
        super(message);
    }
}