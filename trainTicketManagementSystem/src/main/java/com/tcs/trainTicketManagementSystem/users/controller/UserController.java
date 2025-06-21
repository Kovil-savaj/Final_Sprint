package com.tcs.trainTicketManagementSystem.users.controller;

import com.tcs.trainTicketManagementSystem.users.dto.LoginRequest;
import com.tcs.trainTicketManagementSystem.users.dto.LoginResponse;
import com.tcs.trainTicketManagementSystem.users.dto.PasswordResetRequest;
import com.tcs.trainTicketManagementSystem.users.dto.UserRegistrationRequest;
import com.tcs.trainTicketManagementSystem.users.dto.UserResponse;
import com.tcs.trainTicketManagementSystem.users.exception.UserAlreadyExistsException;
import com.tcs.trainTicketManagementSystem.users.exception.UserNotFoundException;
import com.tcs.trainTicketManagementSystem.users.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for User operations.
 */
@RestController
@RequestMapping("/api/v1/users")
@Validated
@Tag(name = "User Management", description = "APIs for managing users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "Register a new user", description = "Creates a new user account with validation")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created successfully",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "User already exists")
    })
    public ResponseEntity<UserResponse> registerUser(
            @Parameter(description = "User registration details", required = true)
            @Valid @RequestBody UserRegistrationRequest request) {
        logger.info("Received user registration request for username: {}", request.getUsername());
        UserResponse response = userService.registerUser(request);
        logger.info("User registered successfully with ID: {}", response.getUserId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    @Operation(summary = "Authenticate a user", description = "Authenticates a user and returns a login response")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login successful",
            content = @Content(schema = @Schema(implementation = LoginResponse.class))),
        @ApiResponse(responseCode = "401", description = "Login failed")
    })
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest request) {
        logger.info("Received login request for username/email: {}", request.getUsernameOrEmail());
        
        LoginResponse response = userService.authenticateUser(request);
        
        if (response.isAuthenticated()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", description = "Retrieves user information by user ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> getUserById(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long userId) {
        logger.debug("Fetching user by ID: {}", userId);
        UserResponse response = userService.getUserById(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/username/{username}")
    @Operation(summary = "Get user by username", description = "Retrieves user information by username")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> getUserByUsername(
            @Parameter(description = "Username", required = true)
            @PathVariable String username) {
        logger.debug("Fetching user by username: {}", username);
        UserResponse response = userService.getUserByUsername(username);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/email/{email}")
    @Operation(summary = "Get user by email", description = "Retrieves user information by email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> getUserByEmail(
            @Parameter(description = "Email address", required = true)
            @PathVariable String email) {
        logger.debug("Fetching user by email: {}", email);
        UserResponse response = userService.getUserByEmail(email);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieves all users in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    })
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        logger.debug("Fetching all users");
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/role/{role}")
    @Operation(summary = "Get users by role", description = "Retrieves all users with a specific role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid role")
    })
    public ResponseEntity<List<UserResponse>> getUsersByRole(
            @Parameter(description = "User role (USER or ADMIN)", required = true)
            @PathVariable String role) {
        logger.debug("Fetching users by role: {}", role);
        List<UserResponse> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search/username")
    @Operation(summary = "Search users by username", description = "Searches users by username pattern")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search completed successfully",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    })
    public ResponseEntity<List<UserResponse>> searchUsersByUsername(
            @Parameter(description = "Username pattern to search for", required = true)
            @RequestParam String username) {
        logger.debug("Searching users by username pattern: {}", username);
        List<UserResponse> users = userService.searchUsersByUsername(username);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search/email")
    @Operation(summary = "Search users by email", description = "Searches users by email pattern")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search completed successfully",
            content = @Content(schema = @Schema(implementation = UserResponse.class)))
    })
    public ResponseEntity<List<UserResponse>> searchUsersByEmail(
            @Parameter(description = "Email pattern to search for", required = true)
            @RequestParam String email) {
        logger.debug("Searching users by email pattern: {}", email);
        List<UserResponse> users = userService.searchUsersByEmail(email);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user", description = "Updates user information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "409", description = "Username or email already exists")
    })
    public ResponseEntity<UserResponse> updateUser(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long userId,
            @Parameter(description = "Updated user information", required = true)
            @Valid @RequestBody UserRegistrationRequest request) {
        logger.info("Updating user with ID: {}", userId);
        UserResponse response = userService.updateUser(userId, request);
        logger.info("User updated successfully with ID: {}", response.getUserId());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{userId}/password")
    @Operation(summary = "Reset user password", description = "Resets the password for a specific user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Password reset successfully",
            content = @Content(schema = @Schema(implementation = UserResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid password format"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<UserResponse> resetPassword(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long userId,
            @Parameter(description = "Password reset details", required = true)
            @Valid @RequestBody PasswordResetRequest request) {
        logger.info("Resetting password for user with ID: {}", userId);
        UserResponse response = userService.resetPassword(userId, request);
        logger.info("Password reset successfully for user with ID: {}", response.getUserId());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete user", description = "Deletes a user by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "User ID", required = true)
            @PathVariable Long userId) {
        logger.info("Deleting user with ID: {}", userId);
        userService.deleteUser(userId);
        logger.info("User deleted successfully with ID: {}", userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/username/{username}")
    @Operation(summary = "Check if username exists", description = "Checks if a username already exists")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Check completed successfully")
    })
    public ResponseEntity<Boolean> existsByUsername(
            @Parameter(description = "Username to check", required = true)
            @PathVariable String username) {
        logger.debug("Checking if username exists: {}", username);
        boolean exists = userService.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/email/{email}")
    @Operation(summary = "Check if email exists", description = "Checks if an email already exists")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Check completed successfully")
    })
    public ResponseEntity<Boolean> existsByEmail(
            @Parameter(description = "Email to check", required = true)
            @PathVariable String email) {
        logger.debug("Checking if email exists: {}", email);
        boolean exists = userService.existsByEmail(email);
        return ResponseEntity.ok(exists);
    }
} 