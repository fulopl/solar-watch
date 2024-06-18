package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.GeocodingResponse;
import org.springframework.web.client.RestTemplate;

public class GeocodingService {
    private static final String API_KEY = System.getenv("API_KEY");
    private RestTemplate restTemplate;

    public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GeocodingResponse getGeocodes(String address) {
        String url = "http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid=" + API_KEY;
    }

}
