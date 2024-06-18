package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.SunRiseSunSetReport;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

public class SunriseSunsetService {
    private final RestTemplate restTemplate;

    public SunriseSunsetService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public SunRiseSunSetReport getSunriseSunsetReport(float lat, float lng, LocalDate date) {
        String url = "https://api.sunrise-sunset.org/json?lat=36.7201600&lng=-4.4203400&date=2024-06-18";
    }
}
