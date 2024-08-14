package com.codecool.solarwatch.model;

import java.time.LocalDate;

public record SunRiseSunSetTimeDTO(String city, String state, String countryCode, LocalDate date
        , String sunrise, String sunset) {}
