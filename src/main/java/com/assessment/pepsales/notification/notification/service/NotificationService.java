package com.assessment.pepsales.notification.notification.service;

import java.util.List;

import com.assessment.pepsales.notification.notification.model.Notification;
import com.assessment.pepsales.notification.notification.model.NotificationType;

public interface NotificationService {
    void sendNotification(int userId, String content, NotificationType type);
    List<Notification> getUserNotifications(int userId);
} 