package com.tcs.trainTicketManagementSystem.train.exception;

/**
 * Exception thrown when a train already exists.
 */
public class TrainAlreadyExistsException extends RuntimeException {

    public TrainAlreadyExistsException(String message) {
        super(message);
    }

    public TrainAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public static TrainAlreadyExistsException withName(String trainName) {
        return new TrainAlreadyExistsException("Train with name '" + trainName + "' already exists");
    }

    public static TrainAlreadyExistsException withRoute(String source, String destination, String trainName) {
        return new TrainAlreadyExistsException("Train '" + trainName + "' already exists for route: " + source + " to " + destination);
    }
} 