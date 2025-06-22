package com.tcs.trainTicketManagementSystem.train.dto;

import java.util.List;

/**
 * DTO for station list response containing distinct source and destination stations.
 */
public class StationListResponse {
    
    private List<String> sourceStations;
    private List<String> destinationStations;
    
    // Default constructor
    public StationListResponse() {}
    
    // Constructor with all fields
    public StationListResponse(List<String> sourceStations, List<String> destinationStations) {
        this.sourceStations = sourceStations;
        this.destinationStations = destinationStations;
    }
    
    // Getters and Setters
    public List<String> getSourceStations() {
        return sourceStations;
    }
    
    public void setSourceStations(List<String> sourceStations) {
        this.sourceStations = sourceStations;
    }
    
    public List<String> getDestinationStations() {
        return destinationStations;
    }
    
    public void setDestinationStations(List<String> destinationStations) {
        this.destinationStations = destinationStations;
    }
    
    @Override
    public String toString() {
        return "StationListResponse{" +
                "sourceStations=" + sourceStations +
                ", destinationStations=" + destinationStations +
                '}';
    }
} 