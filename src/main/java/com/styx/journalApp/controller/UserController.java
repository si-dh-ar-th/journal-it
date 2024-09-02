package com.styx.journalApp.controller;

import com.styx.journalApp.entity.User;
import com.styx.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    admin level requests. removed from user

//    @GetMapping
//    public ResponseEntity<?> getAll(){
//        List<User> allEntries = userService.getAllEntries();
//        if(allEntries != null && !allEntries.isEmpty()){
//            return new ResponseEntity<>(allEntries, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

//    @GetMapping("/{userName}")
//    public ResponseEntity<User> getUserByUserName(@PathVariable String userName){
//        User user = userService.findByUserName(userName);
//        if(user != null){
//            return new ResponseEntity<>(user, HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

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

}
