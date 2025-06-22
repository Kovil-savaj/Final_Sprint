package com.tcs.trainTicketManagementSystem.train.dto;

import java.math.BigDecimal;

/**
 * DTO for admin dashboard statistics response.
 */
public class AdminDashboardResponse {
    
    private Long totalBookings;
    private BigDecimal totalSales;
    private Long confirmedBookings;
    private Long cancelledBookings;
    private Long activeTrains;
    private Long inactiveTrains;
    private Long totalTrains;
    private Long totalUsers;
    private Long totalPassengers;
    
    // Default constructor
    public AdminDashboardResponse() {}
    
    // Constructor with all fields
    public AdminDashboardResponse(Long totalBookings, BigDecimal totalSales, Long confirmedBookings, 
                                 Long cancelledBookings, Long activeTrains, Long inactiveTrains, 
                                 Long totalTrains, Long totalUsers, Long totalPassengers) {
        this.totalBookings = totalBookings;
        this.totalSales = totalSales;
        this.confirmedBookings = confirmedBookings;
        this.cancelledBookings = cancelledBookings;
        this.activeTrains = activeTrains;
        this.inactiveTrains = inactiveTrains;
        this.totalTrains = totalTrains;
        this.totalUsers = totalUsers;
        this.totalPassengers = totalPassengers;
    }
    
    // Getters and Setters
    public Long getTotalBookings() {
        return totalBookings;
    }
    
    public void setTotalBookings(Long totalBookings) {
        this.totalBookings = totalBookings;
    }
    
    public BigDecimal getTotalSales() {
        return totalSales;
    }
    
    public void setTotalSales(BigDecimal totalSales) {
        this.totalSales = totalSales;
    }
    
    public Long getConfirmedBookings() {
        return confirmedBookings;
    }
    
    public void setConfirmedBookings(Long confirmedBookings) {
        this.confirmedBookings = confirmedBookings;
    }
    
    public Long getCancelledBookings() {
        return cancelledBookings;
    }
    
    public void setCancelledBookings(Long cancelledBookings) {
        this.cancelledBookings = cancelledBookings;
    }
    
    public Long getActiveTrains() {
        return activeTrains;
    }
    
    public void setActiveTrains(Long activeTrains) {
        this.activeTrains = activeTrains;
    }
    
    public Long getInactiveTrains() {
        return inactiveTrains;
    }
    
    public void setInactiveTrains(Long inactiveTrains) {
        this.inactiveTrains = inactiveTrains;
    }
    
    public Long getTotalTrains() {
        return totalTrains;
    }
    
    public void setTotalTrains(Long totalTrains) {
        this.totalTrains = totalTrains;
    }
    
    public Long getTotalUsers() {
        return totalUsers;
    }
    
    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
    }
    
    public Long getTotalPassengers() {
        return totalPassengers;
    }
    
    public void setTotalPassengers(Long totalPassengers) {
        this.totalPassengers = totalPassengers;
    }
    
    @Override
    public String toString() {
        return "AdminDashboardResponse{" +
                "totalBookings=" + totalBookings +
                ", totalSales=" + totalSales +
                ", confirmedBookings=" + confirmedBookings +
                ", cancelledBookings=" + cancelledBookings +
                ", activeTrains=" + activeTrains +
                ", inactiveTrains=" + inactiveTrains +
                ", totalTrains=" + totalTrains +
                ", totalUsers=" + totalUsers +
                ", totalPassengers=" + totalPassengers +
                '}';
    }
} 