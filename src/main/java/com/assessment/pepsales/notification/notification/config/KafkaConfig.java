package com.assessment.pepsales.notification.notification.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic emailTopic() {
        return TopicBuilder.name("notifications.email")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic smsTopic() {
        return TopicBuilder.name("notifications.sms")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic inAppTopic() {
        return TopicBuilder.name("notifications.in_app")
                .partitions(1)
                .replicas(1)
                .build();
    }
} 