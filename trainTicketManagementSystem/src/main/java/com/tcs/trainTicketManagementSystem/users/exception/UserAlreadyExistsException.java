package com.tcs.trainTicketManagementSystem.users.exception;

/**
 * Exception thrown when a user already exists in the system.
 */
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public static UserAlreadyExistsException withUsername(String username) {
        return new UserAlreadyExistsException("User already exists with username: " + username);
    }

    public static UserAlreadyExistsException withEmail(String email) {
        return new UserAlreadyExistsException("User already exists with email: " + email);
    }
} 