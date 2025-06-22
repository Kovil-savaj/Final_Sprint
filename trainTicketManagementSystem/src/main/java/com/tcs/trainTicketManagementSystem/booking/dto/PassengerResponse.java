package com.tcs.trainTicketManagementSystem.booking.dto;

/**
 * DTO for passenger information in responses.
 */
public class PassengerResponse {

    private Long passengerId;
    private Long bookingId;
    private String name;
    private Integer age;
    private String gender;
    private String idProof;

    // Default constructor
    public PassengerResponse() {}

    // Constructor with fields
    public PassengerResponse(Long passengerId, Long bookingId, String name, Integer age, String gender, String idProof) {
        this.passengerId = passengerId;
        this.bookingId = bookingId;
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

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
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
        return "PassengerResponse{" +
                "passengerId=" + passengerId +
                ", bookingId=" + bookingId +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", idProof='" + idProof + '\'' +
                '}';
    }
} 