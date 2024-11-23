package com.codecool.solarwatch.errorhandling;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.View;

import java.util.NoSuchElementException;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    public ControllerAdvice(View error) {
    }

    @ResponseBody
    @ExceptionHandler(InvalidLocationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage invalidLocationHandler(InvalidLocationException ex) {
        return new ErrorMessage(ex.getMessage());
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

    @ResponseBody
    @ExceptionHandler(InvalidApiKeyException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage invalidApiKeyHandler(InvalidApiKeyException ex) {
        return new ErrorMessage(ex.getMessage());
    }
}
