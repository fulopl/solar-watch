package com.codecool.solarwatch.service;

import com.codecool.solarwatch.model.City;
import com.codecool.solarwatch.model.GeocodingPlace;
import com.codecool.solarwatch.model.GeocodingResponse;
import com.codecool.solarwatch.repository.CityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GeocodingService {
    private static final String API_KEY = System.getenv("API_KEY");
    private static final Logger logger = LoggerFactory.getLogger(GeocodingResponse.class);
    //private RestTemplate restTemplate;
    private CityRepository cityRepository;
    private WebClient webClient;

    @Autowired
    public GeocodingService(WebClient webClient, CityRepository cityRepository) {
        this.webClient = webClient;
        this.cityRepository = cityRepository;
    }

    public City getGeocodingPlace(String cityName) {
        City city = cityRepository.findByName(cityName).orElse(null);
        if (city == null) city = getPlaceFromOpenWeatherAPI(cityName);
        return city;
    }

    private City getPlaceFromOpenWeatherAPI(String cityName) throws ArrayIndexOutOfBoundsException, NullPointerException {
        String url = String.format("http://api.openweathermap.org/geo/1.0/direct"
                + "?q=%s&limit=2&appid=%s", cityName, API_KEY);
        //GeocodingPlace[] places = restTemplate.getForObject(url, GeocodingPlace[].class);

        //WebClient start
        GeocodingPlace[] places = webClient
                .get()
                .uri(url)
                .retrieve()
                .bodyToMono(GeocodingPlace[].class)
                .block();
        //WebClient end
        logger.info("Response from Open Weather API: {}", places[0]);
        City city = new City();
        city.setName(places[0].name());
        city.setState(places[0].state());
        city.setCountry(places[0].country());
        city.setLatitude(places[0].lat());
        city.setLongitude(places[0].lon());
        cityRepository.save(city);
        return city;
    }

}
