package com.styx.journalApp.controller;

import com.styx.journalApp.entity.JournalEntry;
import com.styx.journalApp.entity.User;
import com.styx.journalApp.service.JournalEntryService;
import com.styx.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalsOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> allEntries = user.getJournalEntries();
        if(allEntries != null && !allEntries.isEmpty()){
            return new ResponseEntity<>(allEntries, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{journalId}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId journalId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        Optional<JournalEntry> entry = journalEntryService.getEntryById(journalId);

        if(entry.isPresent()){
            List<JournalEntry> filteredEntries = user.getJournalEntries().stream().filter(x -> x.getId().equals(journalId)).collect(Collectors.toList());
            if(!filteredEntries.isEmpty()){
                return new ResponseEntity<>(entry.get(), HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public  ResponseEntity<?> createJournal(@RequestBody JournalEntry journalEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        try {
            journalEntryService.saveEntry(journalEntry, userName);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/id/{journalId}")
    public ResponseEntity<JournalEntry> updateJournalById(@PathVariable ObjectId journalId, @RequestBody JournalEntry newEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        Optional<JournalEntry> entry = journalEntryService.getEntryById(journalId);

        if(entry.isPresent()){
            List<JournalEntry> filteredEntries = user.getJournalEntries().stream().filter(x -> x.getId().equals(journalId)).collect(Collectors.toList());
            if(!filteredEntries.isEmpty()){
                JournalEntry oldEntry = entry.get();
                oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
                oldEntry.setDescription(newEntry.getDescription() != null && !newEntry.getDescription().isEmpty() ? newEntry.getDescription() : oldEntry.getDescription());
                journalEntryService.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            } else{
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{journalId}")
    public ResponseEntity<?> deleteJournal(@PathVariable ObjectId journalId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        JournalEntry entryById = journalEntryService.getEntryById(journalId).orElse(null);
        if(entryById != null){
            List<JournalEntry> filteredEntries = userService.findByUserName(userName).getJournalEntries().stream().filter(x -> x.getId().equals(journalId)).collect(Collectors.toList());
            if(!filteredEntries.isEmpty()){
                journalEntryService.deleteUserJournal(journalId, userName);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else{
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
