package com.example.bbs.controller;

import com.example.bbs.exception.DataAlreadyExitsException;
import com.example.bbs.response.BBSResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BBSControllerAdvice {

    @ExceptionHandler(DataAlreadyExitsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public BBSResponse dataExits(DataAlreadyExitsException e) {
        return new BBSResponse(404, e.getMessage());
    }
}
