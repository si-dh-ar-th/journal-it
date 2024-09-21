package com.styx.journalApp.cache;

import com.styx.journalApp.entity.ConfigJournalAppEntry;
import com.styx.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    @Autowired
    private ConfigJournalAppRepository configJournalAppRepository;

    public Map<String, String> APP_CACHE = new HashMap<>();

    @PostConstruct
    public void initialize(){
        List<ConfigJournalAppEntry> allEntries = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntry entry : allEntries) {
            APP_CACHE.put(entry.getKey(), entry.getValue());
        }
    }

}
