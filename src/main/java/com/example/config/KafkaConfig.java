package com.example.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.UUID;

@Configuration
public class KafkaConfig
{

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

//    @Bean
//    public KafkaAdmin kafkaAdmin()
//    {
//        Map<String, Object> configs = new HashMap<>();
//        configs.put("bootstrap.servers", bootstrapServers);
//        return new KafkaAdmin(configs);
//    }

    @Bean
    public NewTopic demoTopic()
    {
        return TopicBuilder
            .name("demo-topic")
            .partitions(1)
            .replicas(1)
            .build();
    }

    @Bean
    public NewTopic postConstructTopic()
    {
        return new NewTopic("post-construct-topic-" + UUID.randomUUID(), 1, (short) 1);
    }

    @Bean
    public NewTopic applicationReadyTopic()
    {
        return new NewTopic("application-ready-topic" + UUID.randomUUID(), 1, (short) 1);
    }
} 