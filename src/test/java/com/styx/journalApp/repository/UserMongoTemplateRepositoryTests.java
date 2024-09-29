package com.styx.journalApp.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserMongoTemplateRepositoryTests {

    @Autowired
    private UserMongoTemplateRepository userMongoTemplateRepository;

    @Test
    void getUsersForSATest() {
        Assertions.assertNotNull(userMongoTemplateRepository.getUsersForSA());
    }

}
