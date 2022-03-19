package com.example.bbs.exception;

public class CannotFindDataException extends RuntimeException{
    public CannotFindDataException(String name) {
        super(name);
    }
}
