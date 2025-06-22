package com.tcs.trainTicketManagementSystem.booking.model;

import jakarta.persistence.*;

/**
 * Entity representing a passenger in the system.
 */
@Entity
@Table(name = "passenger")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passenger_id")
    private Long passengerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "age", nullable = false)
    private Integer age;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "id_proof", nullable = false, length = 50)
    private String idProof; // Aadhar card number (12 digits)

    // Default constructor
    public Passenger() {}

    // Constructor with required fields
    public Passenger(Booking booking, String name, Integer age, String gender, String idProof) {
        this.booking = booking;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.idProof = idProof;
    }

    // Getters and Setters
    public Long getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(Long passengerId) {
        this.passengerId = passengerId;
    }

    public Booking getBooking() {
        return booking;
    }

    public void setBooking(Booking booking) {
        this.booking = booking;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "passengerId=" + passengerId +
                ", bookingId=" + (booking != null ? booking.getBookingId() : null) +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", idProof='" + idProof + '\'' +
                '}';
    }
} 