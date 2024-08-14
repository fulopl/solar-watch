package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.model.GeocodingPlace;
import com.codecool.solarwatch.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.sql.Array;


class GeocodingServiceTest {
    private static final Logger log = LoggerFactory.getLogger(GeocodingServiceTest.class);
    private GeocodingService underTest;
    private CityRepository cityRepository = Mockito.mock(CityRepository.class);
    private WebClient webClient = Mockito.mock(WebClient.class);
    private GeocodingPlace[] testPlaces;


    @BeforeEach
    void setUp() {
        underTest = new GeocodingService(webClient, cityRepository);
        testPlaces = new GeocodingPlace[1];
        testPlaces[0] = new GeocodingPlace("London", 51.5073219, -0.1276474
                , "GB", "England");
    }

    @Test
    void testGetPlaceFromOpenWeatherAPI_givenValidCityName_thenReturnCityEntity() {
        //arrange
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(GeocodingPlace[].class)).thenReturn(Mono.just(testPlaces));

        //act
        City result = underTest.getPlaceFromOpenWeatherAPI("London");

        //assert
        assertNotNull(result);
        assertEquals("London", result.getName());
        assertEquals("GB", result.getCountry());
        assertEquals(51.5073219, result.getLatitude());
        assertEquals(-0.1276474, result.getLongitude());

        verify(cityRepository, times(1)).save(any(City.class));
        verify(cityRepository, times(1)).save(result);
        verify(webClient, times(1)).get();

    }

    @Test
    void testGetPlaceFromOpenWeatherAPI_givenInvalidCityName_thenThrowsException() {
        //arrange
        WebClient.RequestHeadersUriSpec requestHeadersUriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec requestHeadersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(GeocodingPlace[].class)).thenReturn(Mono.just(new GeocodingPlace[0]));

        //act

        //assert
        assertThrows(ArrayIndexOutOfBoundsException.class, ()->underTest.getPlaceFromOpenWeatherAPI("Abcxxxxxyz"));
    }

    @Test
    void testGetGeocodingPlace_givenExistingCityInDB() {
        //TODO
        //verify(cityRepository, times(1)).findByName("London");
    }
}