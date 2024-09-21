package com.styx.journalApp.repository;

import com.styx.journalApp.entity.ConfigJournalAppEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntry, ObjectId> {

}
