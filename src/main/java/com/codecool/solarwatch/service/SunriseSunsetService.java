package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.*;
import com.codecool.solarwatch.repository.SunRiseSunSetTimeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;

@Service
public class SunriseSunsetService {
    private final RestTemplate restTemplate;
    private final SunRiseSunSetTimeRepository sunRiseSunSetTimeRepository;
    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(SunRiseSunSetResults.class);

    @Autowired
    public SunriseSunsetService(RestTemplate restTemplate, WebClient webClient, SunRiseSunSetTimeRepository sunRiseSunSetTimeRepository) {
        this.restTemplate = restTemplate;
        this.webClient = webClient;
        this.sunRiseSunSetTimeRepository = sunRiseSunSetTimeRepository;
    }

    public SunRiseSunSetTime getSunriseSunsetTime(LocalDate date, City city) throws NullPointerException{
        SunRiseSunSetTime sunRiseSunSetTime = sunRiseSunSetTimeRepository
                .findByCityAndLocalDate(city, date).orElse(null);
        if (sunRiseSunSetTime == null) {
            SunRiseSunSetResults sunRiseSunSetResults = getSunriseSunsetTimeFromAPI(city.getLatitude(), city.getLongitude(), date);
            sunRiseSunSetTime = new SunRiseSunSetTime();
            sunRiseSunSetTime.setLocalDate(date);
            sunRiseSunSetTime.setCity(city);
            sunRiseSunSetTime.setSunRise(sunRiseSunSetResults.sunrise());
            sunRiseSunSetTime.setSunSet(sunRiseSunSetResults.sunset());
            sunRiseSunSetTimeRepository.save(sunRiseSunSetTime);
        }
        return sunRiseSunSetTime;
    }

    private SunRiseSunSetResults getSunriseSunsetTimeFromAPI(double lat, double lng, LocalDate date)
            throws NullPointerException{

        String url = String.format("https://api.sunrise-sunset.org/json" +
                "?lat=%s&lng=%s&date=%tF", lat, lng, date);

        System.out.println(url);

        SunRiseSunSetResponse sunRiseSunSetResponse = restTemplate.getForObject(url, SunRiseSunSetResponse.class);
//        SunRiseSunSetResponse sunRiseSunSetResponse =  webClient.get()
//                .uri(url)
//                .retrieve()
//                .bodyToMono(SunRiseSunSetResponse.class)
//                .block()
//                ;
        logger.info("Response from Open Weather API: {}", sunRiseSunSetResponse);
        return sunRiseSunSetResponse.sunRiseSunSetResults();
    }
}
