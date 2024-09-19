package com.styx.journalApp.service;

import com.styx.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    private static final String API_KEY = System.getenv("WEATHER_API_KEY");

    private static final String baseUri = "http://api.weatherapi.com/v1";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getCurrentWeather(String location){
        String endpoint = "/current.json?key={{API_KEY}}&q={{location}}".replace("{{API_KEY}}", API_KEY)
                                                                        .replace("{{location}}", location);
        String requestUrl = baseUri + endpoint;

//      deserialization of response to pojo
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(requestUrl, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }

}
