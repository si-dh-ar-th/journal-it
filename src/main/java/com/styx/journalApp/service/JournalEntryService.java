package com.styx.journalApp.service;

import com.styx.journalApp.entity.JournalEntry;
import com.styx.journalApp.entity.User;
import com.styx.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void saveEntry(JournalEntry journalEntry, String userName) throws Exception {
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry savedEntry = journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(savedEntry);
            userService.saveExistingUser(user);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Error while saving journal entry : ", e.getCause());
        }
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntry.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAllEntries(){
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getEntryById(ObjectId journalId){
        return journalEntryRepository.findById(journalId);
    }

    @Transactional
    public void deleteUserJournal(ObjectId journalId, String userName){
        try {
            User user = userService.findByUserName(userName);
            user.getJournalEntries().removeIf(x -> x.getId().equals(journalId));
            userService.saveExistingUser(user);
            journalEntryRepository.deleteById(journalId);
        } catch (Exception e){
            System.out.println(e.getMessage());
            throw new RuntimeException("Error occured while deleting journal entry : ", e.getCause());
        }

    }

}
