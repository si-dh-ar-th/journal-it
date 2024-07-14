package com.styx.journalApp.controller;

import com.styx.journalApp.entity.JournalEntry;
import com.styx.journalApp.entity.User;
import com.styx.journalApp.service.JournalEntryService;
import com.styx.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
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

    @GetMapping("/id/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable ObjectId userId){
        Optional<User> entryById = userService.getEntryById(userId);
        if(entryById.isPresent()){
            return new ResponseEntity<>(entryById.get(), HttpStatus.OK);
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

    @PutMapping("/id/{userId}")
    public ResponseEntity<User> updateUserById(@PathVariable ObjectId journalId, @RequestBody User newEntry){
        User oldEntry = userService.getEntryById(journalId).orElse(null);
        if(oldEntry != null){
            oldEntry.setUserName(newEntry.getUserName() != null && !newEntry.getUserName().isEmpty() ? newEntry.getUserName() : oldEntry.getUserName());
            oldEntry.setPassword(newEntry.getPassword() != null && !newEntry.getPassword().isEmpty() ? newEntry.getPassword() : oldEntry.getPassword());
            userService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable ObjectId userId){
        User entryById = userService.getEntryById(userId).orElse(null);
        if(entryById != null){
            userService.deleteEntryById(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
