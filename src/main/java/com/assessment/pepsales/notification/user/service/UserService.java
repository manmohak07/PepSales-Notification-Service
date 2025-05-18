package com.assessment.pepsales.notification.user.service;

import java.util.List;

import com.assessment.pepsales.notification.user.model.User;

public interface UserService {
    List<User> getAllUsers();

    User getUserById(int id);

    void createUser(User user);

    boolean deleteUserById(int id);
} 