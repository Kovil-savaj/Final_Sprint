package com.tcs.trainTicketManagementSystem.train.dto;

import com.tcs.trainTicketManagementSystem.train.model.ClassType;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

/**
 * DTO for fare type creation and update requests with validation.
 */
public class FareTypeRequest {

    @NotNull(message = "Class type is required")
    private ClassType classType;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.01", message = "Price must be greater than 0")
    @Digits(integer = 8, fraction = 2, message = "Price must have at most 8 digits before decimal and 2 after")
    private BigDecimal price;

    @NotNull(message = "Seats available is required")
    @Min(value = 0, message = "Seats available must be 0 or greater")
    @Max(value = 1000, message = "Seats available cannot exceed 1000")
    private Integer seatsAvailable;

    // Default constructor
    public FareTypeRequest() {}

    // Constructor with all fields
    public FareTypeRequest(ClassType classType, BigDecimal price, Integer seatsAvailable) {
        this.classType = classType;
        this.price = price;
        this.seatsAvailable = seatsAvailable;
    }

    // Getters and Setters
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
        return "FareTypeRequest{" +
                "classType=" + classType +
                ", price=" + price +
                ", seatsAvailable=" + seatsAvailable +
                '}';
    }
} 