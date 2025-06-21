package com.tcs.trainTicketManagementSystem.train.dto;

import com.tcs.trainTicketManagementSystem.train.model.Train;
import com.tcs.trainTicketManagementSystem.train.model.TrainStatus;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DTO for train response without sensitive information.
 */
public class TrainResponse {

    private Long trainId;
    private String trainName;
    private String source;
    private String destination;
    private LocalTime departureTime;
    private LocalTime arrivalTime;
    private TrainStatus status;
    private List<String> scheduleDays;
    private List<FareTypeResponse> fareTypes;

    // Default constructor
    public TrainResponse() {}

    // Constructor from Train entity
    public TrainResponse(Train train) {
        this.trainId = train.getTrainId();
        this.trainName = train.getTrainName();
        this.source = train.getSource();
        this.destination = train.getDestination();
        this.departureTime = train.getDepartureTime();
        this.arrivalTime = train.getArrivalTime();
        this.status = train.getStatus();
        
        // Convert schedules to day strings
        if (train.getSchedules() != null) {
            this.scheduleDays = train.getSchedules().stream()
                    .map(schedule -> schedule.getDayOfWeek().name())
                    .collect(Collectors.toList());
        }
        
        // Convert fare types to response DTOs
        if (train.getFareTypes() != null) {
            this.fareTypes = train.getFareTypes().stream()
                    .map(FareTypeResponse::new)
                    .collect(Collectors.toList());
        }
    }

    // Constructor with all fields
    public TrainResponse(Long trainId, String trainName, String source, String destination, 
                        LocalTime departureTime, LocalTime arrivalTime, TrainStatus status) {
        this.trainId = trainId;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.status = status;
    }

    // Getters and Setters
    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

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

    public List<FareTypeResponse> getFareTypes() {
        return fareTypes;
    }

    public void setFareTypes(List<FareTypeResponse> fareTypes) {
        this.fareTypes = fareTypes;
    }

    @Override
    public String toString() {
        return "TrainResponse{" +
                "trainId=" + trainId +
                ", trainName='" + trainName + '\'' +
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