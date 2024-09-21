package com.styx.journalApp.service;

import com.styx.journalApp.api.response.WeatherResponse;
import com.styx.journalApp.cache.AppCache;
import com.styx.journalApp.resources.WeatherAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    private static final String API_KEY = System.getenv("WEATHER_API_KEY");

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherResponse getCurrentWeather(String location){
        String requestUrl = appCache.APP_CACHE.get(WeatherAPI.CURRENT_WEATHER_JSON.toString()).replace("<api_key>", API_KEY).replace("<location>", location);

//      deserialization of response to pojo
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(requestUrl, HttpMethod.GET, null, WeatherResponse.class);
        return response.getBody();
    }

}
