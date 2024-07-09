package com.styx.journalApp.controller;

import com.styx.journalApp.entity.JournalEntry;
import com.styx.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll(){
        return journalEntryService.getAllEntries();
    }

    @GetMapping("/id/{journalId}")
    public JournalEntry getJournalById(@PathVariable ObjectId journalId){
        return journalEntryService.getEntryById(journalId).orElse(null);
    }

    @PostMapping
    public JournalEntry createJournal(@RequestBody JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(journalEntry);
        return journalEntry;
    }

    @PutMapping("/id/{journalId}")
    public boolean updateJournalById(@PathVariable ObjectId journalId, @RequestBody JournalEntry newEntry){
        JournalEntry oldEntry = journalEntryService.getEntryById(journalId).orElse(null);
        if(oldEntry != null){
            oldEntry.setName(newEntry.getName() != null && !newEntry.getName().isEmpty() ? newEntry.getName() : oldEntry.getName());
            oldEntry.setDescription(newEntry.getDescription() != null && !newEntry.getDescription().isEmpty() ? newEntry.getDescription() : oldEntry.getDescription());
        }
        journalEntryService.saveEntry(oldEntry);
        return true;
    }

    @DeleteMapping("/id/{journalId}")
    public boolean deleteJournal(@PathVariable ObjectId journalId){
        journalEntryService.deleteEntryById(journalId);
        return true;
    }

}
