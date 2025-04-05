package com.example.exception;

public class ConflictRequestException extends RuntimeException {
    
    public ConflictRequestException(String message){
        super(message);
    }
}
