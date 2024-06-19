package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.SunRiseSunSetReport;
import com.codecool.solarwatch.model.SunRiseSunSetResponse;
import com.codecool.solarwatch.model.SunRiseSunSetResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class SunriseSunsetService {
    private final RestTemplate restTemplate;

    public SunriseSunsetService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    public SunRiseSunSetResult getSunriseSunsetReport(float lat, float lng, LocalDate date) throws NullPointerException{
        String url = String.format("https://api.sunrise-sunset.org/json" +
                "?lat=%s&lng=%s&date=%tF", lat, lng, date);

        SunRiseSunSetResponse response = restTemplate.getForObject(url, SunRiseSunSetResponse.class);
        return response.results();
    }
}
