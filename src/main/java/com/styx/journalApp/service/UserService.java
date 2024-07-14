package com.styx.journalApp.service;

import com.styx.journalApp.entity.User;
import com.styx.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveEntry(User user) {
        userRepository.save(user);
    }

    public List<User> getAllEntries(){
        return userRepository.findAll();
    }

    public Optional<User> getEntryById(ObjectId userId){
        return userRepository.findById(userId);
    }

    public void deleteEntryById(ObjectId journalId){
        userRepository.deleteById(journalId);
    }

}
