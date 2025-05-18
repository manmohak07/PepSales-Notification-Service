package com.assessment.pepsales.notification.notification.service;

import java.util.Collections;
import java.util.List;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.assessment.pepsales.notification.notification.model.Notification;
import com.assessment.pepsales.notification.notification.model.NotificationType;
import com.assessment.pepsales.notification.notification.repository.NotificationRepository;
import com.assessment.pepsales.notification.user.model.User;
import com.assessment.pepsales.notification.user.service.UserService;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserService userService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   UserService userService,
                                   KafkaTemplate<String, String> kafkaTemplate) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendNotification(int userId, String content, NotificationType type) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new IllegalArgumentException("User with ID " + userId + " does not exist");
        }

        boolean shouldSend = switch (type) {
            case EMAIL -> user.isAllowsEmail();
            case SMS -> user.isAllowsSMS();
            case IN_APP -> user.isAllowsInApp();
        };

        if (!shouldSend) {
            return;
        }

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setContent(content);
        notification.setType(type);
        notificationRepository.save(notification);

        String topic = "notifications." + type.name().toLowerCase();
        kafkaTemplate.send(topic, content);
    }

    @Override
    public List<Notification> getUserNotifications(int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return Collections.emptyList();
        }
        return notificationRepository.findByUser(user);
    }
} 