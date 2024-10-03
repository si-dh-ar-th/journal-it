package com.styx.journalApp.scheduler;

import com.styx.journalApp.cache.AppCache;
import com.styx.journalApp.entity.JournalEntry;
import com.styx.journalApp.entity.User;
import com.styx.journalApp.repository.UserMongoTemplateRepository;
import com.styx.journalApp.resources.Sentiment;
import com.styx.journalApp.service.EmailService;
import com.styx.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            List<Sentiment> filteredEntriesSentiment = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCount = new HashMap<>();

            for (Sentiment sentiment : filteredEntriesSentiment) {
                if (sentiment != null) {
                    sentimentCount.put(sentiment, sentimentCount.getOrDefault(sentiment, 0) + 1);
                }
            }

            Sentiment mostFrequentSentiment = null;
            int maxCount = 0;

            for(Map.Entry<Sentiment, Integer> entry : sentimentCount.entrySet()){
                if(maxCount > entry.getValue()){
                    maxCount = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }

            if(mostFrequentSentiment != null){
                emailService.sendEmail(user.getEmail(), "Sentiment for the last 7 days: ", mostFrequentSentiment.toString());
            }
        }
    }

    @Scheduled(cron = "*/10 * * * *")
    public void clearAppCache() {
        appCache.initialize();
    }
}
