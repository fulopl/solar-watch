package com.codecool.solarwatch.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SolarWatchControllerAdvice {

    @ResponseBody
    @ExceptionHandler(InvalidLocationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String invalidLocationHandler(InvalidLocationException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(ThirdPartyServiceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String thirdPartyServiceHandler(ThirdPartyServiceException ex) {
        return ex.getMessage();
    }
}
