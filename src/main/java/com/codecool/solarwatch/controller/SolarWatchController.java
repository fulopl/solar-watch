package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.SunRiseSunSetReport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class SolarWatchController {

    @GetMapping("/sunrise-sunset")
    public SunRiseSunSetReport getSunRiseSunSet(@RequestParam(required = false) LocalDate date
            , @RequestParam(defaultValue = "Budapest") String city) {
        if (date == null) date = LocalDate.now();
        return new SunRiseSunSetReport(city, date, date.atTime(5, 0)
                , date.atTime(21, 0));
    }
}
