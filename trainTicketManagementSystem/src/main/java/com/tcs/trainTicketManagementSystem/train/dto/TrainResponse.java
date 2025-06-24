package com.tcs.trainTicketManagementSystem.train.dto;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import com.tcs.trainTicketManagementSystem.train.model.Train;
import com.tcs.trainTicketManagementSystem.train.model.TrainStatus;

/**
 * DTO for train response without sensitive information.
 */
public class TrainResponse {

    private Long trainId;
    private String trainName;
    private String source;
    private String destination;
    private LocalTime departureTime;
    private int journeyHours;
    private int journeyMinutes;
    private TrainStatus status;
    private List<String> scheduleDays;
    private List<FareTypeResponse> fareTypes;

    // Default constructor
    public TrainResponse() {
    }

    // Constructor from Train entity
    public TrainResponse(Train train) {
        this.trainId = train.getTrainId();
        this.trainName = train.getTrainName();
        this.source = train.getSource();
        this.destination = train.getDestination();
        this.departureTime = train.getDepartureTime();
        this.journeyHours = train.getJourneyHours();
        this.journeyMinutes = train.getJourneyMinutes();
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
            LocalTime departureTime, int journeyHours, int journeyMinutes, TrainStatus status) {
        this.trainId = trainId;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.journeyHours = journeyHours;
        this.journeyMinutes = journeyMinutes;
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

    public List<FareTypeResponse> getFareTypes() {
        return fareTypes;
    }

    public void setFareTypes(List<FareTypeResponse> fareTypes) {
        this.fareTypes = fareTypes;
    }

    /**
     * Computes the arrival time based on departure time and journey duration.
     */
    public LocalTime getComputedArrivalTime() {
        if (departureTime != null) {
            return departureTime.plusHours(journeyHours).plusMinutes(journeyMinutes);
        }
        return null;
    }

    /**
     * Computes the number of days spanned by the journey (0 = same day, 1 =
     * next day, etc.).
     */
    public int getArrivalDayOffset() {
        if (departureTime != null) {
            int totalMinutes = departureTime.getHour() * 60 + departureTime.getMinute() + journeyHours * 60 + journeyMinutes;
            return totalMinutes / (24 * 60);
        }
        return 0;
    }

    @Override
    public String toString() {
        return "TrainResponse{"
                + "trainId=" + trainId
                + ", trainName='" + trainName + '\''
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
