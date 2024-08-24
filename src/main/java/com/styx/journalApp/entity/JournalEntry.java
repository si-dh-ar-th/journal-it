package com.styx.journalApp.entity;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "journalEntry")
@Data   // Combined annotation for @Setter, @Getter, @RequiredArgsConstructor, @ToString, @EqualsAndHashCode, and @Value
//@NoArgsConstructor        constructor with no arguments
//@AllArgsConstructor       constructor with all arguments
//@ToString                 not entirely sure
//@EqualsAndHashCode        not entirely sure
//@Builder                  not entirely sure
public class JournalEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String description;
    private LocalDateTime date;
}
