package com.codecool.solarwatch.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SunRiseSunSetReport(String city, LocalDate date, LocalDateTime sunrise, LocalDateTime sunset) {}
