package com.tcs.trainTicketManagementSystem.train.exception;

/**
 * Exception thrown when a train is not found.
 */
public class TrainNotFoundException extends RuntimeException {

    public TrainNotFoundException(String message) {
        super(message);
    }

    public TrainNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public static TrainNotFoundException withId(Long trainId) {
        return new TrainNotFoundException("Train with ID " + trainId + " not found");
    }

    public static TrainNotFoundException withName(String trainName) {
        return new TrainNotFoundException("Train with name '" + trainName + "' not found");
    }

    public static TrainNotFoundException withRoute(String source, String destination) {
        return new TrainNotFoundException("No trains found for route: " + source + " to " + destination);
    }
} 