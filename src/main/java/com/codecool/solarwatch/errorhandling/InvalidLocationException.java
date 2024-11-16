package com.codecool.solarwatch.errorhandling;

public class InvalidLocationException extends ArrayIndexOutOfBoundsException{
    public InvalidLocationException() {
        super("Unknown location.");
    }
}
