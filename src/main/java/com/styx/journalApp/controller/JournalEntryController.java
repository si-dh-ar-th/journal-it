package com.styx.journalApp.controller;

import com.styx.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<String, JournalEntry> journalEntries = new HashMap<>();

    @GetMapping
    public List<JournalEntry> getAll(){
        return new ArrayList<>(journalEntries.values());
    }

    @GetMapping("/id/{journalId}")
    public JournalEntry getJournalById(@PathVariable Long journalId){
        return journalEntries.get(journalId);
    }

    @PostMapping
    public boolean createJournal(@RequestBody JournalEntry journalEntry){
        journalEntries.put(journalEntry.getId(), journalEntry);
        return true;
    }

    @PutMapping
    public boolean updateJournal(@RequestBody JournalEntry journalEntry){
        journalEntries.put(journalEntry.getId(), journalEntry);
        return true;
    }

    @DeleteMapping("/id/{journalId}")
    public boolean deleteJournal(@PathVariable Long journalId){
        journalEntries.remove(journalId);
        return true;
    }

}
