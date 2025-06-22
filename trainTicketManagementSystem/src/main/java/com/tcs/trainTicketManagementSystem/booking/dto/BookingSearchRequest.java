package com.tcs.trainTicketManagementSystem.booking.dto;

import java.time.LocalDate;

/**
 * DTO for booking search criteria.
 */
public class BookingSearchRequest {

    private Long userId;
    private Long trainId;
    private Long fareTypeId;
    private LocalDate journeyDateFrom;
    private LocalDate journeyDateTo;
    private LocalDate bookingDateFrom;
    private LocalDate bookingDateTo;
    private String status;
    private String source;
    private String destination;
    private String trainName;
    private String username;

    // Default constructor
    public BookingSearchRequest() {}

    // Constructor with fields
    public BookingSearchRequest(Long userId, Long trainId, Long fareTypeId, LocalDate journeyDateFrom, 
                               LocalDate journeyDateTo, LocalDate bookingDateFrom, LocalDate bookingDateTo,
                               String status, String source, String destination, String trainName, String username) {
        this.userId = userId;
        this.trainId = trainId;
        this.fareTypeId = fareTypeId;
        this.journeyDateFrom = journeyDateFrom;
        this.journeyDateTo = journeyDateTo;
        this.bookingDateFrom = bookingDateFrom;
        this.bookingDateTo = bookingDateTo;
        this.status = status;
        this.source = source;
        this.destination = destination;
        this.trainName = trainName;
        this.username = username;
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

    public LocalDate getJourneyDateFrom() {
        return journeyDateFrom;
    }

    public void setJourneyDateFrom(LocalDate journeyDateFrom) {
        this.journeyDateFrom = journeyDateFrom;
    }

    public LocalDate getJourneyDateTo() {
        return journeyDateTo;
    }

    public void setJourneyDateTo(LocalDate journeyDateTo) {
        this.journeyDateTo = journeyDateTo;
    }

    public LocalDate getBookingDateFrom() {
        return bookingDateFrom;
    }

    public void setBookingDateFrom(LocalDate bookingDateFrom) {
        this.bookingDateFrom = bookingDateFrom;
    }

    public LocalDate getBookingDateTo() {
        return bookingDateTo;
    }

    public void setBookingDateTo(LocalDate bookingDateTo) {
        this.bookingDateTo = bookingDateTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getTrainName() {
        return trainName;
    }

    public void setTrainName(String trainName) {
        this.trainName = trainName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "BookingSearchRequest{" +
                "userId=" + userId +
                ", trainId=" + trainId +
                ", fareTypeId=" + fareTypeId +
                ", journeyDateFrom=" + journeyDateFrom +
                ", journeyDateTo=" + journeyDateTo +
                ", bookingDateFrom=" + bookingDateFrom +
                ", bookingDateTo=" + bookingDateTo +
                ", status='" + status + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", trainName='" + trainName + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
} 