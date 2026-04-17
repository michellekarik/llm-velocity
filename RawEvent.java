package com.llmvelocity.processingengine;   //tells java where this file lives in your project structure

import jakarta.persistence.*;  //library for JPA (Java Persistence API). It contains all the tags like @Id, @Entity that tells Java how to talk to a database
import lombok.Data; //library for tag @Data to avoid Java's getter and setters (-50 lines of code cause this libarary is used)

@Data
@Entity   //treat this class RawEvent as a database table
@Table(name = "raw_events", schema = "llm_pulse")
public class RawEvent {

    @Id  //tells us treat event_id as the primary key of this table
    @Column(name = "event_id")  //Maps the Java variable (eventId) to the actual SQL column name (event_id)
    private String eventId;

    @Column(name = "event_type")
    private String eventType;

    // Remove @JdbcTypeCode and @JdbcType here
    @Column(name = "raw_payload", columnDefinition = "text")  //columnDefinition = "text": Since the JSON data from GitHub is huge, a standard "String" isn't enough. We tell Postgres to use the TEXT type, which can hold massive amounts of data.
    private String rawPayload;

    @Column(name = "processed")
    private boolean processed = false;   //When the Java engine finishes reading a row, it flips this to true. This is the "secret sauce" that ensures we don't process the same event twice
}