package com.codecool.solarwatch.controller;

public class InvalidLocationException extends ArrayIndexOutOfBoundsException{
    public InvalidLocationException() {
        super("Unknown location.");
    }
}
