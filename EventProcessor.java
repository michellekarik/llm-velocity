package com.llmvelocity.processingengine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service //tells the computer: "This file is a Worker." Without this, the file is just a dead piece of text. With it, the computer "hires" this file to stay awake as long as the app is running
public class EventProcessor {

    @Autowired   //worker asks i need my tools. This is the tools sticker and lets this class reach its tools
    private RawEventRepository repository;  //repository: This is the Librarian for the Bronze Tank (Raw Data)

    @Autowired
    private LlmMentionRepository mentionRepository;  //mentionRepository: This is the Librarian for the Silver Tank (Cleaned AI Mentions)

    @Scheduled(fixedDelay = 10000)  //wait for 10 seconds which 10*10^3 milliseconds after you finish the job before starting again
    public void processLatestData() {
        List<RawEvent> unprocessedEvents = repository.findAll().stream()   //repository.findAll(): The worker asks the Bronze Librarian: "Bring me every folder you have"
                .filter(e -> !e.isProcessed())
                .collect(Collectors.toList());

        System.out.println("🔎 Java Scanner: Checking " + unprocessedEvents.size() + " NEW events...");

        for (RawEvent event : unprocessedEvents) {
            String modelFound = detectModel(event.getRawPayload().toLowerCase());

            if (modelFound != null) {
                LlmMention mention = new LlmMention();
                mention.setOriginalEventId(event.getEventId());
                mention.setDetectedModel(modelFound);
                mention.setProcessedAt(LocalDateTime.now());

                mentionRepository.save(mention);
                System.out.println("🔥 LLM DETECTED: " + modelFound);
            }

            event.setProcessed(true);
            repository.save(event);
        }
    }

    // This is the "Brain" of your project
    private String detectModel(String text) {
        // 1. SPECIFIC 2026 FRONTIER MODELS (Highest Priority)
        if (text.contains("o3-mini") || text.contains("gpt-o1") || text.contains("gpt-5")) return "OpenAI (GPT-o3/5)";
        if (text.contains("llama-4") || text.contains("llama-3.2")) return "Meta (Llama 4)";
        if (text.contains("claude-4") || text.contains("sonnet-3.7") || text.contains("claude code")) return "Anthropic (Claude 4)";
        if (text.contains("deepseek-v3") || text.contains("deepseek-r1")) return "DeepSeek (V3/R1)";
        if (text.contains("gemini-2") || text.contains("gemini-1.5")) return "Google (Gemini 2)";

        // 2. BRAND NAMES (Catch-all for companies)
        if (text.contains("openai") || text.contains("chatgpt")) return "OpenAI-General";
        if (text.contains("meta") && text.contains("llama")) return "Meta-Llama";
        if (text.contains("anthropic") || text.contains("claude")) return "Anthropic-Claude";
        if (text.contains("mistral") || text.contains("mixtral")) return "Mistral-AI";

        // 3. THE FALLBACK (Everything else)
        if (text.contains("ai") || text.contains("llm") || text.contains("agent")) return "General-AI";

        return null;
    }
}