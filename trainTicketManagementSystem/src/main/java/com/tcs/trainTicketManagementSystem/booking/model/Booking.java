package com.tcs.trainTicketManagementSystem.booking.model;

import com.tcs.trainTicketManagementSystem.train.model.FareType;
import com.tcs.trainTicketManagementSystem.train.model.Train;
import com.tcs.trainTicketManagementSystem.users.model.User;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Entity representing a booking in the system.
 */
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Long bookingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fare_type_id", nullable = false)
    private FareType fareType;

    @Column(name = "journey_date", nullable = false)
    private LocalDate journeyDate;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "total_fare", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalFare;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 15)
    private BookingStatus status = BookingStatus.CONFIRMED;

    // Relationship with passengers
    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Passenger> passengers;

    // Default constructor
    public Booking() {
        this.bookingDate = LocalDate.now();
        this.status = BookingStatus.CONFIRMED;
    }

    // Constructor with required fields
    public Booking(User user, Train train, FareType fareType, LocalDate journeyDate, BigDecimal totalFare) {
        this();
        this.user = user;
        this.train = train;
        this.fareType = fareType;
        this.journeyDate = journeyDate;
        this.totalFare = totalFare;
    }

    // Getters and Setters
    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public FareType getFareType() {
        return fareType;
    }

    public void setFareType(FareType fareType) {
        this.fareType = fareType;
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

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", userId=" + (user != null ? user.getUserId() : null) +
                ", trainId=" + (train != null ? train.getTrainId() : null) +
                ", fareTypeId=" + (fareType != null ? fareType.getFareTypeId() : null) +
                ", journeyDate=" + journeyDate +
                ", bookingDate=" + bookingDate +
                ", totalFare=" + totalFare +
                ", status=" + status +
                '}';
    }
} 