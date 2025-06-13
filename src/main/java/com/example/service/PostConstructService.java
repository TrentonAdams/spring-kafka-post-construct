package com.example.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Profile("post-construct")
public class PostConstructService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final NewTopic postConstructTopic;

    public PostConstructService(KafkaTemplate<String, String> kafkaTemplate, NewTopic postConstructTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.postConstructTopic = postConstructTopic;
        log.info("Creating PostConstructService with topic: {}", postConstructTopic.name());
    }

    @PostConstruct
    public void init() {
        // This will fail because the topic hasn't been created yet
        // The @PostConstruct runs before SmartInitializingSingleton
        log.info("Attempting to send message to topic: {}", postConstructTopic.name());
        kafkaTemplate.send(postConstructTopic.name(), "test-message-from-post-construct");
    }
} 