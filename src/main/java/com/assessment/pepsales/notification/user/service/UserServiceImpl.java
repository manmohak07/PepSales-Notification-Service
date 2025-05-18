package com.assessment.pepsales.notification.user.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.assessment.pepsales.notification.user.model.User;
import com.assessment.pepsales.notification.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }
    
    @Override
    public void createUser(User user) {
        userRepository.save(user);
    }
    
    @Override
    public boolean deleteUserById(int id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }
} 