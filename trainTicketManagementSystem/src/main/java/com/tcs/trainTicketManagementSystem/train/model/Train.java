package com.tcs.trainTicketManagementSystem.train.model;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Entity representing a train in the system.
 */
@Entity
@Table(name = "train")
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "train_id")
    private Long trainId;

    @Column(name = "train_name", nullable = false, length = 100)
    private String trainName;

    @Column(name = "source", nullable = false, length = 100)
    private String source;

    @Column(name = "destination", nullable = false, length = 100)
    private String destination;

    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime;

    @Column(name = "journey_hours", nullable = false)
    private int journeyHours;

    @Column(name = "journey_minutes", nullable = false)
    private int journeyMinutes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10)
    private TrainStatus status = TrainStatus.ACTIVE;

    // Relationships
    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TrainSchedule> schedules;

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FareType> fareTypes;

    // Default constructor
    public Train() {
    }

    // Constructor with all fields
    public Train(String trainName, String source, String destination, LocalTime departureTime, int journeyHours, int journeyMinutes, TrainStatus status) {
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.journeyHours = journeyHours;
        this.journeyMinutes = journeyMinutes;
        this.status = status != null ? status : TrainStatus.ACTIVE;
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

    public List<TrainSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<TrainSchedule> schedules) {
        this.schedules = schedules;
    }

    public List<FareType> getFareTypes() {
        return fareTypes;
    }

    public void setFareTypes(List<FareType> fareTypes) {
        this.fareTypes = fareTypes;
    }

    /**
     * Computes the arrival time based on departure time and journey duration.
     */
    public LocalTime getComputedArrivalTime() {
        return departureTime.plusHours(journeyHours).plusMinutes(journeyMinutes);
    }

    /**
     * Computes the number of days spanned by the journey (0 = same day, 1 =
     * next day, etc.).
     */
    public int getArrivalDayOffset() {
        int totalMinutes = departureTime.getHour() * 60 + departureTime.getMinute() + journeyHours * 60 + journeyMinutes;
        return totalMinutes / (24 * 60);
    }

    @Override
    public String toString() {
        return "Train{"
                + "trainId=" + trainId
                + ", trainName='" + trainName + '\''
                + ", source='" + source + '\''
                + ", destination='" + destination + '\''
                + ", departureTime=" + departureTime
                + ", journeyHours=" + journeyHours
                + ", journeyMinutes=" + journeyMinutes
                + ", status=" + status
                + '}';
    }
}
