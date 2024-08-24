package com.styx.journalApp.controller;

import com.styx.journalApp.entity.User;
import com.styx.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<User> allEntries = userService.getAllEntries();
        if(allEntries != null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{userName}")
    public ResponseEntity<User> getUserByUserName(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        if(user != null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public  ResponseEntity<?> createUser(@RequestBody User user){
        try {
            userService.saveEntry(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{userName}")
    public ResponseEntity<User> updateUserById(@PathVariable String userName, @RequestBody User newData){
        User oldData = userService.findByUserName(userName);
        if(oldData != null){
            oldData.setUserName(!newData.getUserName().isEmpty() ? newData.getUserName() : oldData.getUserName());
            oldData.setPassword(!newData.getPassword().isEmpty() ? newData.getPassword() : oldData.getPassword());
            userService.saveEntry(oldData);
            return new ResponseEntity<>(oldData, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @DeleteMapping("/{userName}")
//    public ResponseEntity<?> deleteUser(@PathVariable String userName){
//        User user = userService.findByUserName(userName);
//        if(user != null){
//            userService.deleteEntryById(user.getId());
//            return new ResponseEntity<>(HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }

}
