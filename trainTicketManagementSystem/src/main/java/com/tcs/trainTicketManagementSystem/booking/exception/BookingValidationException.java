package com.tcs.trainTicketManagementSystem.booking.exception;

/**
 * Exception thrown when booking validation fails.
 */
public class BookingValidationException extends RuntimeException {

    public BookingValidationException(String message) {
        super(message);
    }

    public BookingValidationException(String message, Throwable cause) {
        super(message, cause);
    }
} 