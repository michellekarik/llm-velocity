package com.llmvelocity.processingengine;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "llm_mentions", schema = "llm_pulse")  //this is our silver table llm_mentions

public class LlmMention {
    @Id  //primary key tag
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //automatically increment each record and taken here as the primary key of this silver table
    private Long id;

    private String originalEventId;  //pulled from the bronze table eventId
    private String detectedModel; // e.g., "GPT-4" or "Llama-3"   //pulled from the bronze table raw_payload
    private LocalDateTime processedAt;  //not puled from the bronze table its created based on timestamp.now()
}
