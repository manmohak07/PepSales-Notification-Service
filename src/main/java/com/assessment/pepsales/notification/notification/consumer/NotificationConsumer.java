package com.assessment.pepsales.notification.notification.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {
    private static final Logger logger = LoggerFactory.getLogger(NotificationConsumer.class);

    @KafkaListener(topics = "notifications.email", groupId = "notification-group")
    public void consumeEmailNotification(String message) {
        logger.info("New EMAIL notification received {}", message);
    }

    @KafkaListener(topics = "notifications.sms", groupId = "notification-group")
    public void consumeSmsNotification(String message) {
        logger.info("New SMS notification received {}", message);
    }

    @KafkaListener(topics = "notifications.in_app", groupId = "notification-group")
    public void consumeInAppNotification(String message) {
        logger.info("New IN-APP notification received {}", message);
    }
} 