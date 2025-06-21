package com.tcs.trainTicketManagementSystem.users.service;

import com.tcs.trainTicketManagementSystem.users.dto.LoginRequest;
import com.tcs.trainTicketManagementSystem.users.dto.LoginResponse;
import com.tcs.trainTicketManagementSystem.users.dto.PasswordResetRequest;
import com.tcs.trainTicketManagementSystem.users.dto.UserRegistrationRequest;
import com.tcs.trainTicketManagementSystem.users.dto.UserResponse;

import java.util.List;

/**
 * Service interface for user management operations.
 */
public interface UserService {

    /**
     * Register a new user.
     *
     * @param request User registration request
     * @return UserResponse containing user details
     */
    UserResponse registerUser(UserRegistrationRequest request);

    /**
     * Authenticate a user with username/email and password.
     *
     * @param request Login request containing credentials
     * @return LoginResponse with authentication result
     */
    LoginResponse authenticateUser(LoginRequest request);

    /**
     * Get user by ID.
     *
     * @param userId User ID
     * @return UserResponse containing user details
     */
    UserResponse getUserById(Long userId);

    /**
     * Get user by username.
     *
     * @param username Username
     * @return UserResponse containing user details
     */
    UserResponse getUserByUsername(String username);

    /**
     * Get user by email.
     *
     * @param email Email address
     * @return UserResponse containing user details
     */
    UserResponse getUserByEmail(String email);

    /**
     * Get all users.
     *
     * @return List of UserResponse objects
     */
    List<UserResponse> getAllUsers();

    /**
     * Get users by role.
     *
     * @param role User role
     * @return List of UserResponse objects
     */
    List<UserResponse> getUsersByRole(String role);

    /**
     * Search users by username pattern.
     *
     * @param username Username pattern to search for
     * @return List of UserResponse objects
     */
    List<UserResponse> searchUsersByUsername(String username);

    /**
     * Search users by email pattern.
     *
     * @param email Email pattern to search for
     * @return List of UserResponse objects
     */
    List<UserResponse> searchUsersByEmail(String email);

    /**
     * Update user information.
     *
     * @param userId User ID
     * @param request Updated user information
     * @return UserResponse containing updated user details
     */
    UserResponse updateUser(Long userId, UserRegistrationRequest request);

    /**
     * Reset user password.
     *
     * @param userId User ID
     * @param request Password reset request
     * @return UserResponse containing user details
     */
    UserResponse resetPassword(Long userId, PasswordResetRequest request);

    /**
     * Delete user.
     *
     * @param userId User ID
     */
    void deleteUser(Long userId);

    /**
     * Check if user exists by username.
     *
     * @param username Username to check
     * @return true if user exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if user exists by email.
     *
     * @param email Email to check
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String email);
} 