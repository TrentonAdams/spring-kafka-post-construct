package com.example.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile("application-ready")
public class ApplicationReadyService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final NewTopic applicationReadyTopic;

    public ApplicationReadyService(KafkaTemplate<String, String> kafkaTemplate, NewTopic applicationReadyTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.applicationReadyTopic = applicationReadyTopic;
        log.info("Creating ApplicationReadyService with topic: {}", applicationReadyTopic.name());
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        // This will work because it runs after all beans are initialized
        log.info("Application is ready, sending message to topic: {}", applicationReadyTopic.name());
        kafkaTemplate.send(applicationReadyTopic.name(), "test-message-from-application-ready");
    }
} 