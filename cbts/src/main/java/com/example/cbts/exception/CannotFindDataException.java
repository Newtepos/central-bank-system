package com.example.cbts.exception;

public class CannotFindDataException extends RuntimeException{
    public CannotFindDataException(String name) {
        super(name);
    }
}
