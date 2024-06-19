package com.codecool.solarwatch.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SunRiseSunSetReport(String city, String countryCode, LocalDate date, String sunrise, String sunset) {}
