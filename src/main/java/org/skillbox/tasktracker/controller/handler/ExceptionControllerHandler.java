package org.skillbox.tasktracker.controller.handler;

import org.skillbox.tasktracker.dto.ExceptionResponse;
import org.skillbox.tasktracker.exception.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse existsException(EntityNotFoundException  exception) {
        return new ExceptionResponse(exception.getMessage());
    }

}
