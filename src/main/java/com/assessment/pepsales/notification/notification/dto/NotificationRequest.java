package com.assessment.pepsales.notification.notification.dto;

import com.assessment.pepsales.notification.notification.model.NotificationType;

public class NotificationRequest {
    private int userId;
    private String content;
    private NotificationType type;

    public int getUserId() {
        return userId;
    }

    public String getContent() {
        return content;
    }

    public NotificationType getType() {
        return type;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }
} 