package com.tcs.trainTicketManagementSystem.booking.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for creating a new booking.
 */
public class BookingRequest {

    @NotNull(message = "User ID is required")
    @Min(value = 1, message = "User ID must be a positive number")
    private Long userId;

    @NotNull(message = "Train ID is required")
    @Min(value = 1, message = "Train ID must be a positive number")
    private Long trainId;

    @NotNull(message = "Fare type ID is required")
    @Min(value = 1, message = "Fare type ID must be a positive number")
    private Long fareTypeId;

    @NotNull(message = "Journey date is required")
    @Future(message = "Journey date must be in the future")
    private LocalDate journeyDate;

    @NotNull(message = "Total fare is required")
    @DecimalMin(value = "0.01", message = "Total fare must be at least 0.01")
    @DecimalMax(value = "999999.99", message = "Total fare cannot exceed 999999.99")
    @Digits(integer = 8, fraction = 2, message = "Total fare must have at most 8 digits before decimal and 2 after")
    private BigDecimal totalFare;

    @NotEmpty(message = "At least one passenger is required")
    @Size(max = 10, message = "Maximum 10 passengers allowed per booking")
    private List<PassengerRequest> passengers;

    // Default constructor
    public BookingRequest() {}

    // Constructor with fields
    public BookingRequest(Long userId, Long trainId, Long fareTypeId, LocalDate journeyDate, BigDecimal totalFare, List<PassengerRequest> passengers) {
        this.userId = userId;
        this.trainId = trainId;
        this.fareTypeId = fareTypeId;
        this.journeyDate = journeyDate;
        this.totalFare = totalFare;
        this.passengers = passengers;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public Long getFareTypeId() {
        return fareTypeId;
    }

    public void setFareTypeId(Long fareTypeId) {
        this.fareTypeId = fareTypeId;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public BigDecimal getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(BigDecimal totalFare) {
        this.totalFare = totalFare;
    }

    public List<PassengerRequest> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerRequest> passengers) {
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return "BookingRequest{" +
                "userId=" + userId +
                ", trainId=" + trainId +
                ", fareTypeId=" + fareTypeId +
                ", journeyDate=" + journeyDate +
                ", totalFare=" + totalFare +
                ", passengers=" + passengers +
                '}';
    }
} 