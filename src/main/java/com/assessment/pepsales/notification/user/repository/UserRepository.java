package com.assessment.pepsales.notification.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.assessment.pepsales.notification.user.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {} 