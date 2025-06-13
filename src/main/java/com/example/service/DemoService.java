package com.example.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DemoService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final NewTopic demoTopic;

    public DemoService(KafkaTemplate<String, String> kafkaTemplate, NewTopic demoTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.demoTopic = demoTopic;
        log.info("Creating DemoService with topic: {}", demoTopic.name());
    }

    // @PostConstruct
    // public void init() {
    //     // This will fail because the topic hasn't been created yet
    //     // The @PostConstruct runs before SmartInitializingSingleton
    //     log.info("Attempting to send message to topic: {}", demoTopic.name());
    //     kafkaTemplate.send(demoTopic.name(), "test-message");
    // }


    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        // This will work because it runs after all beans are initialized
        log.info("Application is ready, sending message to topic: {}", demoTopic.name());
        kafkaTemplate.send(demoTopic.name(), "test-message-after-ready");
    }
} 