package com.tcs.trainTicketManagementSystem.train.dto;

import com.tcs.trainTicketManagementSystem.train.model.TrainStatus;
import jakarta.validation.constraints.*;
import java.time.LocalTime;
import java.util.List;

/**
 * DTO for train creation and update requests with validation.
 */
public class TrainRequest {

    @NotBlank(message = "Train name is required")
    @Size(min = 2, max = 100, message = "Train name must be between 2 and 100 characters")
    private String trainName;

    @NotBlank(message = "Source station is required")
    @Size(min = 2, max = 100, message = "Source station must be between 2 and 100 characters")
    private String source;

    @NotBlank(message = "Destination station is required")
    @Size(min = 2, max = 100, message = "Destination station must be between 2 and 100 characters")
    private String destination;

    @NotNull(message = "Departure time is required")
    private LocalTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalTime arrivalTime;

    private TrainStatus status = TrainStatus.ACTIVE;

    private List<String> scheduleDays;

    private List<FareTypeRequest> fareTypes;

    // Default constructor
    public TrainRequest() {}

    // Constructor with all fields
    public TrainRequest(String trainName, String source, String destination, LocalTime departureTime, LocalTime arrivalTime, TrainStatus status) {
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.status = status != null ? status : TrainStatus.ACTIVE;
    }

    // Getters and Setters
    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public TrainStatus getStatus() {
        return status;
    }

    public void setStatus(TrainStatus status) {
        this.status = status;
    }

    public List<String> getScheduleDays() {
        return scheduleDays;
    }

    public void setScheduleDays(List<String> scheduleDays) {
        this.scheduleDays = scheduleDays;
    }

    public List<FareTypeRequest> getFareTypes() {
        return fareTypes;
    }

    public void setFareTypes(List<FareTypeRequest> fareTypes) {
        this.fareTypes = fareTypes;
    }

    @Override
    public String toString() {
        return "TrainRequest{" +
                "trainName='" + trainName + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", status=" + status +
                ", scheduleDays=" + scheduleDays +
                ", fareTypes=" + fareTypes +
                '}';
    }
} 