package com.styx.journalApp.scheduler;

import com.styx.journalApp.cache.AppCache;
import com.styx.journalApp.entity.JournalEntry;
import com.styx.journalApp.entity.User;
import com.styx.journalApp.repository.UserMongoTemplateRepository;
import com.styx.journalApp.service.EmailService;
import com.styx.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

public class Scheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserMongoTemplateRepository userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Autowired
    private AppCache appCache;

    @Scheduled(cron = "0 0 9 * * SUN")
    public void fetchUserAndSendSaMail() {
        List<User> users = userRepository.getUsersForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<String> filteredEntriesContent = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getDescription()).collect(Collectors.toList());
            String entry = String.join(" ", filteredEntriesContent);
            String sentiment = sentimentAnalysisService.getSentiment(entry);
            emailService.sendEmail(user.getEmail(), "Sentiment for the last 7 days", sentiment);
        }
    }

    @Scheduled(cron = "*/10 * * * *")
    public void clearAppCache() {
        appCache.initialize();
    }
}
