package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.*;
import com.codecool.solarwatch.service.GeocodingService;
import com.codecool.solarwatch.service.SunriseSunsetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class SolarWatchController {
    private final GeocodingService geocodingService;
    private final SunriseSunsetService sunriseSunsetService;

    public SolarWatchController(GeocodingService geocodingService, SunriseSunsetService sunriseSunsetService) {
        this.geocodingService = geocodingService;
        this.sunriseSunsetService = sunriseSunsetService;
    }

    @GetMapping("/sunrise-sunset")
    public SunRiseSunSetReport getSunRiseSunSet(@RequestParam(required = false) LocalDate date
            , @RequestParam(defaultValue = "Budapest") String city) {
        if (date == null) date = LocalDate.now();

        City place;
        try {
            place = geocodingService.getGeocodingPlace(city);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidLocationException();
        } catch (NullPointerException e) {
            throw new ThirdPartyServiceException();
        }

        SunRiseSunSetTime sunRiseSunSetTime;
        try {
            sunRiseSunSetTime = sunriseSunsetService
                    .getSunriseSunsetReport(place.getLatitude(), place.getLongitude(), date, place.getId());
        } catch (NullPointerException e) {
            throw new ThirdPartyServiceException();
        }
        return new SunRiseSunSetReport(
                place.getName(),
                place.getState(),
                place.getCountry(),
                date,
                sunRiseSunSetTime.getSunRise(),
                sunRiseSunSetTime.getSunSet()
        );
    }

    @GetMapping("/current")
    public SunRiseSunSetReport getCurrent() {
        SunRiseSunSetTime sunRiseSunSetTime = sunriseSunsetService
                .getSunriseSunsetReport(47.00F, 19.00F, LocalDate.now(), 1);
        return new SunRiseSunSetReport(
                "Budapest",
                "",
                "HU",
                LocalDate.now(),
                sunRiseSunSetTime.getSunRise(),
                sunRiseSunSetTime.getSunSet()
        );
    }

}
