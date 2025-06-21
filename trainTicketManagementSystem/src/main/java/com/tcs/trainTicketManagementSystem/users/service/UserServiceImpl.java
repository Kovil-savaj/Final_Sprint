package com.tcs.trainTicketManagementSystem.users.service;

import com.tcs.trainTicketManagementSystem.users.dto.LoginRequest;
import com.tcs.trainTicketManagementSystem.users.dto.LoginResponse;
import com.tcs.trainTicketManagementSystem.users.dto.PasswordResetRequest;
import com.tcs.trainTicketManagementSystem.users.dto.UserRegistrationRequest;
import com.tcs.trainTicketManagementSystem.users.dto.UserResponse;
import com.tcs.trainTicketManagementSystem.users.exception.UserAlreadyExistsException;
import com.tcs.trainTicketManagementSystem.users.exception.UserNotFoundException;
import com.tcs.trainTicketManagementSystem.users.model.User;
import com.tcs.trainTicketManagementSystem.users.model.UserRole;
import com.tcs.trainTicketManagementSystem.users.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of UserService interface.
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse registerUser(UserRegistrationRequest request) {
        logger.info("Registering new user with username: {}", request.getUsername());

        // Check if user already exists
        if (userRepository.existsByUsername(request.getUsername())) {
            logger.warn("User registration failed: username {} already exists", request.getUsername());
            throw UserAlreadyExistsException.withUsername(request.getUsername());
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            logger.warn("User registration failed: email {} already exists", request.getEmail());
            throw UserAlreadyExistsException.withEmail(request.getEmail());
        }

        // Create new user
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Encrypt password
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());

        User savedUser = userRepository.save(user);
        logger.info("User registered successfully with ID: {}", savedUser.getUserId());

        return new UserResponse(savedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public LoginResponse authenticateUser(LoginRequest request) {
        logger.info("Authenticating user with username/email: {}", request.getUsernameOrEmail());

        // Try to find user by username or email
        User user = userRepository.findByUsername(request.getUsernameOrEmail())
                .orElseGet(() -> userRepository.findByEmail(request.getUsernameOrEmail())
                        .orElse(null));

        if (user == null) {
            logger.warn("Authentication failed: user not found with username/email: {}", request.getUsernameOrEmail());
            return new LoginResponse(false, "Invalid username/email or password");
        }

        // Check if password matches using BCrypt's matches() method
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            logger.info("Authentication successful for user: {}", user.getUsername());
            return new LoginResponse(
                true, 
                "Authentication successful", 
                user.getUserId(), 
                user.getUsername(), 
                user.getEmail(), 
                user.getRole()
            );
        } else {
            logger.warn("Authentication failed: incorrect password for user: {}", user.getUsername());
            return new LoginResponse(false, "Invalid username/email or password");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        logger.debug("Fetching user by ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", userId);
                    return UserNotFoundException.withId(userId);
                });

        return new UserResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByUsername(String username) {
        logger.debug("Fetching user by username: {}", username);
        
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    logger.warn("User not found with username: {}", username);
                    return UserNotFoundException.withUsername(username);
                });

        return new UserResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByEmail(String email) {
        logger.debug("Fetching user by email: {}", email);
        
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.warn("User not found with email: {}", email);
                    return UserNotFoundException.withEmail(email);
                });

        return new UserResponse(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getAllUsers() {
        logger.debug("Fetching all users");
        
        return userRepository.findAll()
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getUsersByRole(String role) {
        logger.debug("Fetching users by role: {}", role);
        
        try {
            UserRole userRole = UserRole.valueOf(role.toUpperCase());
            return userRepository.findByRole(userRole.name())
                    .stream()
                    .map(UserResponse::new)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid role provided: {}", role);
            throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> searchUsersByUsername(String username) {
        logger.debug("Searching users by username pattern: {}", username);
        
        return userRepository.findByUsernameContainingIgnoreCase(username)
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> searchUsersByEmail(String email) {
        logger.debug("Searching users by email pattern: {}", email);
        
        return userRepository.findByEmailContainingIgnoreCase(email)
                .stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse updateUser(Long userId, UserRegistrationRequest request) {
        logger.info("Updating user with ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", userId);
                    return UserNotFoundException.withId(userId);
                });

        // Check if new username/email already exists (excluding current user)
        if (!user.getUsername().equals(request.getUsername()) && 
            userRepository.existsByUsername(request.getUsername())) {
            logger.warn("User update failed: username {} already exists", request.getUsername());
            throw UserAlreadyExistsException.withUsername(request.getUsername());
        }

        if (!user.getEmail().equals(request.getEmail()) && 
            userRepository.existsByEmail(request.getEmail())) {
            logger.warn("User update failed: email {} already exists", request.getEmail());
            throw UserAlreadyExistsException.withEmail(request.getEmail());
        }

        // Update user fields
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword())); // Encrypt password
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setRole(request.getRole());

        User updatedUser = userRepository.save(user);
        logger.info("User updated successfully with ID: {}", updatedUser.getUserId());

        return new UserResponse(updatedUser);
    }

    @Override
    public UserResponse resetPassword(Long userId, PasswordResetRequest request) {
        logger.info("Resetting password for user with ID: {}", userId);
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    logger.warn("User not found with ID: {}", userId);
                    return UserNotFoundException.withId(userId);
                });

        // Update password with new encrypted password
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        
        User updatedUser = userRepository.save(user);
        logger.info("Password reset successfully for user with ID: {}", updatedUser.getUserId());

        return new UserResponse(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        logger.info("Deleting user with ID: {}", userId);
        
        if (!userRepository.existsById(userId)) {
            logger.warn("User not found with ID: {}", userId);
            throw UserNotFoundException.withId(userId);
        }

        userRepository.deleteById(userId);
        logger.info("User deleted successfully with ID: {}", userId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
} 