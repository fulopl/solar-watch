package com.codecool.solarwatch.controller;

import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.model.SunRiseSunSetTime;
import com.codecool.solarwatch.model.SunRiseSunSetTimeDTO;
import com.codecool.solarwatch.service.GeocodingService;
import com.codecool.solarwatch.service.SunriseSunsetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

public class SolarWatchControllerTest {
    private SolarWatchController underTest;
    private final GeocodingService geocodingService = Mockito.mock(GeocodingService.class);
    private final SunriseSunsetService sunriseSunsetService = Mockito.mock(SunriseSunsetService.class);

    @BeforeEach
    void beforeEachTest() {
        underTest = new SolarWatchController(geocodingService, sunriseSunsetService);
    }

    @Test
    void testGetSunRiseSunSet_GivenCityAndDate_ShouldReturnSunRiseSunSetTimeDTO() {
        //arrange
        String cityName = "London";
        LocalDate date = LocalDate.of(2023, 9, 18);
        City city = new City("London");
        city.setState("England");
        city.setCountry("GB");
        SunRiseSunSetTime sunRiseSunSetTime = new SunRiseSunSetTime(
                1, date, "5:38:19 AM", "6:11:09 PM", city);
        SunRiseSunSetTimeDTO expected = new SunRiseSunSetTimeDTO(
                "London",
                "England",
                "GB",
                date,
                "5:38:19 AM",
                "6:11:09 PM"
        );
        Mockito.when(geocodingService.getGeocodingPlace(cityName)).thenReturn(city);
        Mockito.when(sunriseSunsetService.getSunriseSunsetTime(date, city)).thenReturn(sunRiseSunSetTime);

        //act
        SunRiseSunSetTimeDTO actual = underTest.getSunRiseSunSet(date, cityName);

        //assert
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void testGetSunRiseSunSet_GivenInvalidCity_ShouldReturnSunRiseSunSetTimeDTO() {
        //arrange
        LocalDate date = LocalDate.of(2023, 9, 18);
        String cityName = "XXX";
        Mockito.when(geocodingService.getGeocodingPlace(cityName)).thenThrow(ArrayIndexOutOfBoundsException.class);

        //act and assert
        Assertions.assertThrows(InvalidLocationException.class, () -> underTest.getSunRiseSunSet(date, cityName));
    }

    @Test
    void testGetSunRiseSunSet_GivenNoArguments_ShouldReturnSunRiseSunSetTimeDTOForTodayAndBudapest() {
        //arrange
        LocalDate date = LocalDate.now();
        City city = new City("Budapest");
        city.setState("");
        city.setCountry("HU");
        SunRiseSunSetTime sunRiseSunSetTime = new SunRiseSunSetTime(
                1, date, "5:38:19 AM", "6:11:09 PM", city);
        SunRiseSunSetTimeDTO expected = new SunRiseSunSetTimeDTO(
                "Budapest",
                "",
                "HU",
                date,
                "5:38:19 AM",
                "6:11:09 PM"
        );
        Mockito.when(geocodingService.getGeocodingPlace(null)).thenReturn(city);
        Mockito.when(sunriseSunsetService.getSunriseSunsetTime(date, city)).thenReturn(sunRiseSunSetTime);

        //act
        SunRiseSunSetTimeDTO actual = underTest.getSunRiseSunSet(null, null);

        //assert
        Mockito.verify(sunriseSunsetService, Mockito.times(1)).getSunriseSunsetTime(date, city);
        Assertions.assertEquals(expected, actual);
    }
}
