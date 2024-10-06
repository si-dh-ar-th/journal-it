package com.styx.journalApp.service;

import com.styx.journalApp.api.response.WeatherResponse;
import com.styx.journalApp.cache.AppCache;
import com.styx.journalApp.resources.WeatherAPI;
import com.styx.journalApp.resources.constants.PlaceHolders;
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
    private AppCache appCacheBean;

    @Autowired
    private RedisService redisService;

    public WeatherResponse getCurrentWeather(String location){
        WeatherResponse weatherResponse = redisService.get("weather_of_" + location.split(",")[0].toLowerCase(), WeatherResponse.class);
        if(weatherResponse != null){
            return weatherResponse;
        } else{
            String requestUrl = appCacheBean.appCache.get(WeatherAPI.CURRENT_WEATHER_JSON.toString()).replace(PlaceHolders.API_KEY, API_KEY).replace(PlaceHolders.LOCATION, location);
            // deserialization of response to pojo
            ResponseEntity<WeatherResponse> response = restTemplate.exchange(requestUrl, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse body = response.getBody();
            if(body != null){
                redisService.set("weather_of_" + location.split(",")[0].toLowerCase(), body, 300L);
            }
            return body;
        }
    }

}
