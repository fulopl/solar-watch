package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.errorhandling.InvalidApiKeyException;
import com.codecool.solarwatch.errorhandling.InvalidLocationException;
import com.codecool.solarwatch.errorhandling.ThirdPartyServiceException;
import com.codecool.solarwatch.model.*;
import com.codecool.solarwatch.service.GeocodingService;
import com.codecool.solarwatch.service.SunriseSunsetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("api/sunrise-sunset-times")
public class SolarWatchController {
    private final GeocodingService geocodingService;
    private final SunriseSunsetService sunriseSunsetService;

    @Autowired
    public SolarWatchController(GeocodingService geocodingService, SunriseSunsetService sunriseSunsetService) {
        this.geocodingService = geocodingService;
        this.sunriseSunsetService = sunriseSunsetService;
    }

    @GetMapping
    public SunRiseSunSetTimeDTO getSunRiseSunSet(@RequestParam(required = false) LocalDate date
            , @RequestParam(name = "city", defaultValue = "Budapest") String cityName)
            throws InvalidApiKeyException, InvalidLocationException {

        if (date == null) date = LocalDate.now();
        City city;
        try {
            city = geocodingService.getGeocodingPlace(cityName);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidLocationException();
        } catch (NullPointerException e) {
            throw new ThirdPartyServiceException();
        }

        SunRiseSunSetTime sunRiseSunSetTime;
        try {
            sunRiseSunSetTime = sunriseSunsetService
                    .getSunriseSunsetTime(date, city);
        } catch (NullPointerException e) {
            throw new ThirdPartyServiceException();
        }
        return new SunRiseSunSetTimeDTO(
                city.getName(),
                city.getState(),
                city.getCountry(),
                date,
                sunRiseSunSetTime.getSunRise(),
                sunRiseSunSetTime.getSunSet()
        );
    }
}
