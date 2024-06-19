package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.GeocodingPlace;
import com.codecool.solarwatch.model.GeocodingResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GeocodingService {
    private static final String API_KEY = System.getenv("API_KEY");
    private static final Logger logger = LoggerFactory.getLogger(GeocodingResponse.class);
    private RestTemplate restTemplate;


    public GeocodingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GeocodingPlace getGeocodingPlace(String city) throws ArrayIndexOutOfBoundsException, NullPointerException {
        String url = String.format("http://api.openweathermap.org/geo/1.0/direct"
                + "?q=%s&limit=2&appid=%s", city, API_KEY);
        GeocodingPlace[] places = restTemplate.getForObject(url, GeocodingPlace[].class);
        logger.info("Response from Open Weather API: {}", places[0]);;
        return places[0];
    }

}
