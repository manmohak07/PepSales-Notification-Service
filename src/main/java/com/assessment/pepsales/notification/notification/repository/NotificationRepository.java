package com.assessment.pepsales.notification.notification.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assessment.pepsales.notification.notification.model.Notification;
import com.assessment.pepsales.notification.user.model.User;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findByUser(User user);
} 