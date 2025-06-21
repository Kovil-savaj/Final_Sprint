package com.tcs.trainTicketManagementSystem.users.repository;

import com.tcs.trainTicketManagementSystem.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity operations.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find user by username (case-insensitive).
     * @param username the username to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByUsernameIgnoreCase(String username);

    /**
     * Find user by username (case-sensitive - for backward compatibility).
     * @param username the username to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByUsername(String username);

    /**
     * Find user by email (case-insensitive).
     * @param email the email to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByEmailIgnoreCase(String email);

    /**
     * Find user by email (case-sensitive - for backward compatibility).
     * @param email the email to search for
     * @return Optional containing the user if found
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if user exists by username (case-insensitive).
     * @param username the username to check
     * @return true if user exists, false otherwise
     */
    boolean existsByUsernameIgnoreCase(String username);

    /**
     * Check if user exists by username (case-sensitive - for backward compatibility).
     * @param username the username to check
     * @return true if user exists, false otherwise
     */
    boolean existsByUsername(String username);

    /**
     * Check if user exists by email (case-insensitive).
     * @param email the email to check
     * @return true if user exists, false otherwise
     */
    boolean existsByEmailIgnoreCase(String email);

    /**
     * Check if user exists by email (case-sensitive - for backward compatibility).
     * @param email the email to check
     * @return true if user exists, false otherwise
     */
    boolean existsByEmail(String email);

    /**
     * Find users by role.
     * @param role the role to search for
     * @return List of users with the specified role
     */
    List<User> findByRole(String role);

    /**
     * Find users by username containing the given string (case-insensitive).
     * @param username the username pattern to search for
     * @return List of users matching the pattern
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :username, '%'))")
    List<User> findByUsernameContainingIgnoreCase(@Param("username") String username);

    /**
     * Find users by email containing the given string (case-insensitive).
     * @param email the email pattern to search for
     * @return List of users matching the pattern
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    List<User> findByEmailContainingIgnoreCase(@Param("email") String email);
} 