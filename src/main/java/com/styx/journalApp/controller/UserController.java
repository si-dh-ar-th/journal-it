package com.styx.journalApp.controller;

import com.styx.journalApp.api.response.WeatherResponse;
import com.styx.journalApp.entity.User;
import com.styx.journalApp.service.UserService;
import com.styx.journalApp.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WeatherService weatherService;

    @PutMapping
    public ResponseEntity<User> updateUserById(@RequestBody User newData){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User oldData = userService.findByUserName(userName);
        oldData.setUserName(!newData.getUserName().isEmpty() ? newData.getUserName() : oldData.getUserName());
        oldData.setPassword(!newData.getPassword().isEmpty() ? newData.getPassword() : oldData.getPassword());
        userService.saveNewUser(oldData);
        return new ResponseEntity<>(oldData, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        userService.deleteEntryById(user.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> greeting(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse currentWeather = weatherService.getCurrentWeather("Delhi, India");
        String greeting = "";
        if(currentWeather != null && currentWeather.getCurrent().getTemperature() != currentWeather.getCurrent().getFeelsLike()){
            greeting = ", Today's actual temperature is: " + currentWeather.getCurrent().getTemperature() + " but it feels like: " + currentWeather.getCurrent().getFeelsLike();
        } else if (currentWeather != null && currentWeather.getCurrent().getTemperature() == currentWeather.getCurrent().getFeelsLike()) {
            greeting = ", Today's temperature is: " + currentWeather.getCurrent().getTemperature();
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
    }

}
