package com.tcs.trainTicketManagementSystem.train.dto;

import com.tcs.trainTicketManagementSystem.train.model.TrainStatus;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO for train search requests with various criteria.
 */
public class TrainSearchRequest {

    private String source;
    private String destination;
    private LocalDate journeyDate;
    private LocalTime departureTimeAfter;
    private LocalTime departureTimeBefore;
    private TrainStatus status;
    private String trainName;
    private String dayOfWeek;

    // Default constructor
    public TrainSearchRequest() {}

    // Constructor with common search fields
    public TrainSearchRequest(String source, String destination, LocalDate journeyDate) {
        this.source = source;
        this.destination = destination;
        this.journeyDate = journeyDate;
    }

    // Getters and Setters
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

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public LocalTime getDepartureTimeAfter() {
        return departureTimeAfter;
    }

    public void setDepartureTimeAfter(LocalTime departureTimeAfter) {
        this.departureTimeAfter = departureTimeAfter;
    }

    public LocalTime getDepartureTimeBefore() {
        return departureTimeBefore;
    }

    public void setDepartureTimeBefore(LocalTime departureTimeBefore) {
        this.departureTimeBefore = departureTimeBefore;
    }

    public TrainStatus getStatus() {
        return status;
    }

    public void setStatus(TrainStatus status) {
        this.status = status;
    }

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public String toString() {
        return "TrainSearchRequest{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", journeyDate=" + journeyDate +
                ", departureTimeAfter=" + departureTimeAfter +
                ", departureTimeBefore=" + departureTimeBefore +
                ", status=" + status +
                ", trainName='" + trainName + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                '}';
    }
} 