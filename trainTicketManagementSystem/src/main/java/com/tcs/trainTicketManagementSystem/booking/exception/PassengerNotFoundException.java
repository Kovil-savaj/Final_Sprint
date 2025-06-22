package com.tcs.trainTicketManagementSystem.booking.exception;

/**
 * Exception thrown when a passenger is not found.
 */
public class PassengerNotFoundException extends RuntimeException {

    public PassengerNotFoundException(String message) {
        super(message);
    }

    public PassengerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
} 