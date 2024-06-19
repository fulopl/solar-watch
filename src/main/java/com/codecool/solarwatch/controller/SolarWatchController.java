package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.GeocodingPlace;
import com.codecool.solarwatch.model.SunRiseSunSetReport;
import com.codecool.solarwatch.model.SunRiseSunSetResult;
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

        GeocodingPlace geocodingPlace;
        try {
            geocodingPlace = geocodingService.getGeocodingPlace(city);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new InvalidLocationException();
        } catch (NullPointerException e) {
            throw new ThirdPartyServiceException();
        }

        SunRiseSunSetResult sunRiseSunSetResult;
        try {
            sunRiseSunSetResult = sunriseSunsetService
                    .getSunriseSunsetReport(geocodingPlace.lat(), geocodingPlace.lon(), date);
        } catch (NullPointerException e) {
            throw new ThirdPartyServiceException();
        }
        return new SunRiseSunSetReport(
                geocodingPlace.name(),
                geocodingPlace.country(),
                date,
                sunRiseSunSetResult.sunrise(),
                sunRiseSunSetResult.sunset()
        );
    }

    @GetMapping("/current")
    public SunRiseSunSetReport getCurrent() {
        SunRiseSunSetResult sunRiseSunSetResult = sunriseSunsetService
                .getSunriseSunsetReport(47.00F, 19.00F, LocalDate.now());
        return new SunRiseSunSetReport(
                "Budapest",
                "HU",
                LocalDate.now(),
                sunRiseSunSetResult.sunrise(),
                sunRiseSunSetResult.sunset());
    }

}
