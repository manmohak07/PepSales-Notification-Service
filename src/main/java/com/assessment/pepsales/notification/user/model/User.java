package com.assessment.pepsales.notification.user.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private boolean allowsEmail = true;
    private boolean allowsSMS = true;
    private boolean allowsInApp = true;
    
    // Getters
    public int getId() {
        return id;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public boolean isAllowsEmail() {
        return allowsEmail;
    }
    
    public boolean isAllowsSMS() {
        return allowsSMS;
    }
    
    public boolean isAllowsInApp() {
        return allowsInApp;
    }
    
    // Setters
    public void setId(int id) {
        this.id = id;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void setAllowsEmail(boolean allowsEmail) {
        this.allowsEmail = allowsEmail;
    }
    
    public void setAllowsSMS(boolean allowsSMS) {
        this.allowsSMS = allowsSMS;
    }
    
    public void setAllowsInApp(boolean allowsInApp) {
        this.allowsInApp = allowsInApp;
    }
} 