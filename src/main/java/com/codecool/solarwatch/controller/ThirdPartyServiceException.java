package com.codecool.solarwatch.controller;

public class ThirdPartyServiceException extends NullPointerException{
    public ThirdPartyServiceException() {
        super("Requested service currently unavailable. Try again later.");
    }
}
