package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.SunRiseSunSetTime;
import com.codecool.solarwatch.repository.SunRiseSunSetTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/time")
public class SstController {
    private final SunRiseSunSetTimeRepository sunRiseSunSetTimeRepository;

    @Autowired
    public SstController(SunRiseSunSetTimeRepository sunRiseSunSetTimeRepository) {
        this.sunRiseSunSetTimeRepository = sunRiseSunSetTimeRepository;
    }

    @GetMapping
    private List<SunRiseSunSetTime> getAllSunriseSunsetTimes() {
        return sunRiseSunSetTimeRepository.findAll();
    }

    @DeleteMapping("/{id}")
    private void deleteSunriseSunsetTime(@PathVariable Long id) {
        if (sunRiseSunSetTimeRepository.existsById(id))sunRiseSunSetTimeRepository.deleteById(id);
        else throw new IllegalArgumentException("No such SST.");
    }
}
