package com.example.bbs.exception;

public class DataAlreadyExitsException extends RuntimeException{
    public DataAlreadyExitsException(String name) {
        super(name);
    }
}
