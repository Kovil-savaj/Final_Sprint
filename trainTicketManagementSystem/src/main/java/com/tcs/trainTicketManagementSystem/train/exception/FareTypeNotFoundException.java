package com.tcs.trainTicketManagementSystem.train.exception;

import com.tcs.trainTicketManagementSystem.train.model.ClassType;

/**
 * Exception thrown when a fare type is not found.
 */
public class FareTypeNotFoundException extends RuntimeException {

    public FareTypeNotFoundException(String message) {
        super(message);
    }

    public FareTypeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public static FareTypeNotFoundException withId(Long fareTypeId) {
        return new FareTypeNotFoundException("Fare type with ID " + fareTypeId + " not found");
    }

    public static FareTypeNotFoundException withClassType(Long trainId, ClassType classType) {
        return new FareTypeNotFoundException("Fare type with class " + classType + " not found for train ID " + trainId);
    }
} 