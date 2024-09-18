package com.styx.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    private static final String API_KEY = System.getenv("WEATHER_API_KEY");

    private static final String baseUri = "http://api.weatherapi.com/v1";

    @Autowired
    private RestTemplate restTemplate;

    public String getCurrentWeather(String location){
        String endpoint = "/current.json/key={{API_KEY}}&q={{location}}".replace("{{API_KEY}}", API_KEY)
                                                                        .replace("{{location}}", location);
        String requestUrl = baseUri + endpoint;

//        restTemplate.exchange(requestUrl, HttpMethod.GET, )

        return "";
    }

}
