package com.example.cbts.exception;

public class DataAlreadyExitsException extends RuntimeException{
    public DataAlreadyExitsException(String name) {
        super(name);
    }
}
