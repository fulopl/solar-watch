package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.repository.CityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

public class CityControllerTest {
    private CityController underTest;
    private final CityRepository cityRepository = Mockito.mock(CityRepository.class);
    private List<City> cities;

    @BeforeEach
    void beforeEachTest() {
        underTest = new CityController(cityRepository);
        City city1 = new City("Budapest");
        City city2 = new City("London");
        cities = List.of(city1, city2);
    }

    @Test
    void testGetAllCities_ShouldReturnAllCities() {
        //arrange
        Mockito.when(cityRepository.findAll()).thenReturn(cities);
        //act
        List<City> actual = underTest.getAllCities();
        //assert
        Mockito.verify(cityRepository, Mockito.times(1)).findAll();
        Assertions.assertEquals(cities, actual);
    }

    @Test
    void testDeleteCity_GivenValidCityId_ShouldCallDeleteByIdMethod() {
        //arrange
        Mockito.when(cityRepository.existsById(1L)).thenReturn(true);
        //act
        underTest.deleteCity(1L);
        //assert
        Mockito.verify(cityRepository, Mockito.times(1)).deleteById(1L);
    }

    @Test
    void testDeleteCity_GivenInvalidCityId_ShouldThrowIllegalArgumentException() {
        //arrange
        Mockito.when(cityRepository.existsById(3L)).thenReturn(false);
        //act and assert
        Assertions.assertThrows(IllegalArgumentException.class, ()->underTest.deleteCity(3L));
    }
}
