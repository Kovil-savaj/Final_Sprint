package com.tcs.trainTicketManagementSystem.train.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

/**
 * Entity representing fare types and classes for trains.
 */
@Entity
@Table(name = "fare_type")
public class FareType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fare_type_id")
    private Long fareTypeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @Convert(converter = ClassType.ClassTypeConverter.class)
    @Column(name = "class_type", nullable = false, length = 20)
    private ClassType classType;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "seats_available", nullable = false)
    private Integer seatsAvailable;

    // Default constructor
    public FareType() {}

    // Constructor with fields
    public FareType(Train train, ClassType classType, BigDecimal price, Integer seatsAvailable) {
        this.train = train;
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

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
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
        return "FareType{" +
                "fareTypeId=" + fareTypeId +
                ", trainId=" + (train != null ? train.getTrainId() : null) +
                ", classType=" + classType +
                ", price=" + price +
                ", seatsAvailable=" + seatsAvailable +
                '}';
    }
} 