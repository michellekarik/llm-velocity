package com.llmvelocity.processingengine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ProcessingEngineApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProcessingEngineApplication.class, args);
    }

}
