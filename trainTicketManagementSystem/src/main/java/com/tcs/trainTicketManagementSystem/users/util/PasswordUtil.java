package com.tcs.trainTicketManagementSystem.users.util;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Utility class for password operations.
 */
@Component
public class PasswordUtil {

    private final PasswordEncoder passwordEncoder;

    public PasswordUtil(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Encrypt a plain text password using BCrypt.
     * @param plainPassword the plain text password
     * @return the encrypted password
     */
    public String encryptPassword(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    /**
     * Verify if a plain text password matches an encrypted password.
     * @param plainPassword the plain text password to verify
     * @param encryptedPassword the encrypted password to compare against
     * @return true if passwords match, false otherwise
     */
    public boolean verifyPassword(String plainPassword, String encryptedPassword) {
        return passwordEncoder.matches(plainPassword, encryptedPassword);
    }

    /**
     * Generate a BCrypt hash for a given password (useful for testing).
     * @param password the password to hash
     * @return the BCrypt hash
     */
    public String generateHash(String password) {
        return passwordEncoder.encode(password);
    }
} 