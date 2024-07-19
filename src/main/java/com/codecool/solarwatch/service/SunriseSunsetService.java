package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.SunRiseSunSetReport;
import com.codecool.solarwatch.model.SunRiseSunSetResponse;
import com.codecool.solarwatch.model.SunRiseSunSetResult;
import com.codecool.solarwatch.model.SunRiseSunSetTime;
import com.codecool.solarwatch.repository.SunRiseSunSetTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class SunriseSunsetService {
    private final RestTemplate restTemplate;
    private final SunRiseSunSetTimeRepository sunRiseSunSetTimeRepository;

    @Autowired
    public SunriseSunsetService(RestTemplate restTemplate, SunRiseSunSetTimeRepository sunRiseSunSetTimeRepository) {
        this.restTemplate = restTemplate;
        this.sunRiseSunSetTimeRepository = sunRiseSunSetTimeRepository;
    }

    public SunRiseSunSetTime getSunriseSunsetReport(double lat, double lng, LocalDate date, long cityId) throws NullPointerException{
        SunRiseSunSetTime sunRiseSunSetTime = sunRiseSunSetTimeRepository
                .findByCityIdAndLocalDate(cityId, date).orElse(null);
        if (sunRiseSunSetTime == null) {
            SunRiseSunSetResult sunRiseSunSetResult = getSunriseSunsetTimeFromAPI(lat, lng, date);
            sunRiseSunSetTime = new SunRiseSunSetTime();
            sunRiseSunSetTime.setLocalDate(date);
            sunRiseSunSetTime.setCityId(cityId);
            sunRiseSunSetTime.setSunRise(sunRiseSunSetResult.sunrise());
            sunRiseSunSetTime.setSunSet(sunRiseSunSetResult.sunset());
            sunRiseSunSetTimeRepository.save(sunRiseSunSetTime);
        }
        return sunRiseSunSetTime;
    }

    private SunRiseSunSetResult getSunriseSunsetTimeFromAPI(double lat, double lng, LocalDate date) throws NullPointerException{
        String url = String.format("https://api.sunrise-sunset.org/json" +
                "?lat=%s&lng=%s&date=%tF", lat, lng, date);

        SunRiseSunSetResponse response = restTemplate.getForObject(url, SunRiseSunSetResponse.class);
        return response.results();
    }
}
