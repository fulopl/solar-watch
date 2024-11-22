package com.codecool.solarwatch.errorhandling;

public class InvalidApiKeyException extends Exception {
    public InvalidApiKeyException() {
        super("Invalid API key");
    }
}
