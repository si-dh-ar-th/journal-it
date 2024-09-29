package com.styx.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Disabled
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    void sendEmailTest(){
        emailService.sendEmail("jetob89472@abevw.com", "Spring Boot Test", "Hi aap kaise hai?");
    }

}
