package com.codecool.solarwatch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.View;

import java.util.NoSuchElementException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    private final View error;

    public ControllerAdvice(View error) {
        this.error = error;
    }

    @ResponseBody
    @ExceptionHandler(InvalidLocationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidLocationHandler(InvalidLocationException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ThirdPartyServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String thirdPartyServiceHandler(ThirdPartyServiceException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String noSuchElementHandler(NoSuchElementException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String illegalArgumentHandler(IllegalArgumentException ex) {
        return ex.getMessage();
    }
}
