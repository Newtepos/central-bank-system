package com.example.cbts;

import com.example.cbts.exception.CannotFindDataException;
import com.example.cbts.exception.DataAlreadyExitsException;
import com.example.cbts.response.CBTSResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CBTSControllerAdvice {

    @ExceptionHandler(DataAlreadyExitsException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    public CBTSResponse dataExits(DataAlreadyExitsException e) {
        return new CBTSResponse(404, e.getMessage());
    }

    @ExceptionHandler(CannotFindDataException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CBTSResponse dataNotFound(CannotFindDataException e) {
        return new CBTSResponse(404, e.getMessage());
    }
}
