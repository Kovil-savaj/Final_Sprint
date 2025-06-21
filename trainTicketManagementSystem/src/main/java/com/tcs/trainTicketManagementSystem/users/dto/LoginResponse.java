package com.tcs.trainTicketManagementSystem.users.dto;

import com.tcs.trainTicketManagementSystem.users.model.UserRole;
import java.time.LocalDateTime;

/**
 * DTO for login response.
 */
public class LoginResponse {

    private boolean authenticated;
    private String message;
    private Long userId;
    private String username;
    private String email;
    private UserRole role;
    private LocalDateTime loginTime;

    // Default constructor
    public LoginResponse() {
        this.loginTime = LocalDateTime.now();
    }

    // Constructor for successful login
    public LoginResponse(boolean authenticated, String message, Long userId, String username, String email, UserRole role) {
        this();
        this.authenticated = authenticated;
        this.message = message;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    // Constructor for failed login
    public LoginResponse(boolean authenticated, String message) {
        this();
        this.authenticated = authenticated;
        this.message = message;
    }

    // Getters and Setters
    public boolean isAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "authenticated=" + authenticated +
                ", message='" + message + '\'' +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", loginTime=" + loginTime +
                '}';
    }
} 