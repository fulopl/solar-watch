package com.codecool.solarwatch.integrationtest;

import com.codecool.solarwatch.controller.SolarWatchController;
import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.model.SunRiseSunSetTime;
import com.codecool.solarwatch.model.SunRiseSunSetTimeDTO;
import com.codecool.solarwatch.service.GeocodingService;
import com.codecool.solarwatch.service.SunriseSunsetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class SolarWatchControllerIT {

    @LocalServerPort
    private int port;

    @Autowired
    private SolarWatchController solarWatchController;

    @Autowired
    TestRestTemplate testRestTemplate;

    @MockBean
    private SunriseSunsetService sunriseSunsetService;

    @MockBean
    private GeocodingService geocodingService;

    @Test
    void requestReturnsSunSetSunRiseTimes() {
        //TODO response == null
        //arrange
        City city = new City("Budapest");
        given(geocodingService.getGeocodingPlace("Budapest")).willReturn(city);
        given(sunriseSunsetService.getSunriseSunsetTime(LocalDate.now(), city))
                .willReturn(new SunRiseSunSetTime(99, LocalDate.now()
                        , "3:04:00 AM", "3:04:00 AM", city));

        //act
        SunRiseSunSetTimeDTO response = this.testRestTemplate
                .getForObject("http://localhost:" + port + "/api/sunrise-sunset-times",
                        SunRiseSunSetTimeDTO.class);
        System.out.println(city);
        System.out.println(response);

        //assert
//        assertThat(response.city()).isEqualTo("Budapest");
//        assertThat(response.sunrise()).isEqualTo("3:04:00 AM");
    }
}
