package com.tcs.trainTicketManagementSystem.train.dto;

import com.tcs.trainTicketManagementSystem.train.model.ClassType;
import com.tcs.trainTicketManagementSystem.train.model.FareType;
import java.math.BigDecimal;

/**
 * DTO for fare type response.
 */
public class FareTypeResponse {

    private Long fareTypeId;
    private Long trainId;
    private ClassType classType;
    private BigDecimal price;
    private Integer seatsAvailable;

    // Default constructor
    public FareTypeResponse() {}

    // Constructor from FareType entity
    public FareTypeResponse(FareType fareType) {
        this.fareTypeId = fareType.getFareTypeId();
        this.trainId = fareType.getTrain() != null ? fareType.getTrain().getTrainId() : null;
        this.classType = fareType.getClassType();
        this.price = fareType.getPrice();
        this.seatsAvailable = fareType.getSeatsAvailable();
    }

    // Constructor with all fields
    public FareTypeResponse(Long fareTypeId, Long trainId, ClassType classType, BigDecimal price, Integer seatsAvailable) {
        this.fareTypeId = fareTypeId;
        this.trainId = trainId;
        this.classType = classType;
        this.price = price;
        this.seatsAvailable = seatsAvailable;
    }

    // Getters and Setters
    public Long getFareTypeId() {
        return fareTypeId;
    }

    public void setFareTypeId(Long fareTypeId) {
        this.fareTypeId = fareTypeId;
    }

    public Long getTrainId() {
        return trainId;
    }

    public void setTrainId(Long trainId) {
        this.trainId = trainId;
    }

    public ClassType getClassType() {
        return classType;
    }

    public void setClassType(ClassType classType) {
        this.classType = classType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(Integer seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    @Override
    public String toString() {
        return "FareTypeResponse{" +
                "fareTypeId=" + fareTypeId +
                ", trainId=" + trainId +
                ", classType=" + classType +
                ", price=" + price +
                ", seatsAvailable=" + seatsAvailable +
                '}';
    }
} 