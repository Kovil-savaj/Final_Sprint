package com.tcs.trainTicketManagementSystem.train.dto;

import java.time.LocalTime;
import java.util.List;

import com.tcs.trainTicketManagementSystem.train.model.TrainStatus;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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

    @Min(value = 0, message = "Journey hours must be 0 or greater")
    @Max(value = 168, message = "Journey hours must be less than or equal to 168 (1 week)")
    private int journeyHours;

    @Min(value = 0, message = "Journey minutes must be 0 or greater")
    @Max(value = 59, message = "Journey minutes must be less than 60")
    private int journeyMinutes;

    private TrainStatus status = TrainStatus.ACTIVE;

    private List<String> scheduleDays;

    private List<FareTypeRequest> fareTypes;

    // Default constructor
    public TrainRequest() {
    }

    // Constructor with all fields
    public TrainRequest(String trainName, String source, String destination, LocalTime departureTime, int journeyHours, int journeyMinutes, TrainStatus status) {
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.journeyHours = journeyHours;
        this.journeyMinutes = journeyMinutes;
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

    public int getJourneyHours() {
        return journeyHours;
    }

    public void setJourneyHours(int journeyHours) {
        this.journeyHours = journeyHours;
    }

    public int getJourneyMinutes() {
        return journeyMinutes;
    }

    public void setJourneyMinutes(int journeyMinutes) {
        this.journeyMinutes = journeyMinutes;
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

    /**
     * Validates that the journey duration is positive and reasonable.
     */
    public boolean isValidJourneyDuration() {
        // Journey must be at least 1 minute
        int totalMinutes = journeyHours * 60 + journeyMinutes;
        return totalMinutes > 0 && totalMinutes <= 168 * 60; // Max 1 week
    }

    /**
     * Gets the total journey duration in minutes.
     */
    public int getTotalJourneyMinutes() {
        return journeyHours * 60 + journeyMinutes;
    }

    @Override
    public String toString() {
        return "TrainRequest{"
                + "trainName='" + trainName + '\''
                + ", source='" + source + '\''
                + ", destination='" + destination + '\''
                + ", departureTime=" + departureTime
                + ", journeyHours=" + journeyHours
                + ", journeyMinutes=" + journeyMinutes
                + ", status=" + status
                + ", scheduleDays=" + scheduleDays
                + ", fareTypes=" + fareTypes
                + '}';
    }
}
