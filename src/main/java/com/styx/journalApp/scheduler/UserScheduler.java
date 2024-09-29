package com.styx.journalApp.scheduler;

import com.styx.journalApp.entity.User;
import com.styx.journalApp.repository.UserMongoTemplateRepository;
import com.styx.journalApp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserMongoTemplateRepository userRepository;

    public void fetchUserAndSendSaMail(){
        List<User> users = userRepository.getUsersForSA();
        users.forEach(x -> emailService.sendEmail(x.getEmail(), "Weekly Sentiment Analysis", "This is your sentiment analysis for this week."));
    }

}
