package com.tcs.trainTicketManagementSystem.booking.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO for booking information in responses.
 */
public class BookingResponse {

    private Long bookingId;
    private Long userId;
    private String username;
    private Long trainId;
    private String trainName;
    private String source;
    private String destination;
    private String departureTime;
    private String arrivalTime;
    private Long fareTypeId;
    private String classType;
    private BigDecimal price;
    private LocalDate journeyDate;
    private LocalDate bookingDate;
    private BigDecimal totalFare;
    private String status;
    private List<PassengerResponse> passengers;

    // Default constructor
    public BookingResponse() {}

    // Constructor with fields
    public BookingResponse(Long bookingId, Long userId, String username, Long trainId, String trainName, 
                          String source, String destination, String departureTime, String arrivalTime,
                          Long fareTypeId, String classType, BigDecimal price, LocalDate journeyDate,
                          LocalDate bookingDate, BigDecimal totalFare, String status, List<PassengerResponse> passengers) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.username = username;
        this.trainId = trainId;
        this.trainName = trainName;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.fareTypeId = fareTypeId;
        this.classType = classType;
        this.price = price;
        this.journeyDate = journeyDate;
        this.bookingDate = bookingDate;
        this.totalFare = totalFare;
        this.status = status;
        this.passengers = passengers;
    }

    // Getters and Setters
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Long getFareTypeId() {
        return fareTypeId;
    }

    public void setFareTypeId(Long fareTypeId) {
        this.fareTypeId = fareTypeId;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(LocalDate journeyDate) {
        this.journeyDate = journeyDate;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public BigDecimal getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(BigDecimal totalFare) {
        this.totalFare = totalFare;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<PassengerResponse> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerResponse> passengers) {
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return "BookingResponse{" +
                "bookingId=" + bookingId +
                ", userId=" + userId +
                ", username='" + username + '\'' +
                ", trainId=" + trainId +
                ", trainName='" + trainName + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", departureTime='" + departureTime + '\'' +
                ", arrivalTime='" + arrivalTime + '\'' +
                ", fareTypeId=" + fareTypeId +
                ", classType='" + classType + '\'' +
                ", price=" + price +
                ", journeyDate=" + journeyDate +
                ", bookingDate=" + bookingDate +
                ", totalFare=" + totalFare +
                ", status='" + status + '\'' +
                ", passengers=" + passengers +
                '}';
    }
} 