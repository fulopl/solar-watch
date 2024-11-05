package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.repository.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/city")
public class CityController {
    private final CityRepository cityRepository;

    @Autowired
    public CityController(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    @GetMapping
    private List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @DeleteMapping("/{id}")
    private void deleteCity(@PathVariable Long id) {
        if (cityRepository.existsById(id)) cityRepository.deleteById(id);
        else throw new IllegalArgumentException("No such city.");
    }
}
