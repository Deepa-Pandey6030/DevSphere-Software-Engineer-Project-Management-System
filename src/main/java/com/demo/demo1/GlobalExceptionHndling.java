package com.demo.demo1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHndling{
    
    @ExceptionHandler(SoftwareEngineerNotFoundException.class)
    public ResponseEntity<String>handleSoftwareEngineerNotFound(SoftwareEngineerNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

}
