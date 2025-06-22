package com.tcs.trainTicketManagementSystem.booking.dto;

import jakarta.validation.constraints.*;

/**
 * DTO for passenger details in a booking request.
 */
public class PassengerRequest {

    @NotBlank(message = "Passenger name is required")
    @Size(min = 2, max = 100, message = "Passenger name must be between 2 and 100 characters")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Passenger name can only contain letters and spaces")
    private String name;

    @NotNull(message = "Passenger age is required")
    @Min(value = 1, message = "Age must be at least 1")
    @Max(value = 120, message = "Age cannot exceed 120")
    private Integer age;

    @NotBlank(message = "Gender is required")
    @Pattern(regexp = "^(MALE|FEMALE|OTHER)$", message = "Gender must be MALE, FEMALE, or OTHER")
    private String gender;

    @NotBlank(message = "Aadhar card number is required")
    @Pattern(regexp = "^[0-9]{12}$", message = "Aadhar card number must be exactly 12 digits")
    private String idProof; // Aadhar card number (12 digits)

    // Default constructor
    public PassengerRequest() {}

    // Constructor with fields
    public PassengerRequest(String name, Integer age, String gender, String idProof) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.idProof = idProof;
    }

    // Getters and Setters
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
        return "PassengerRequest{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                ", idProof='" + idProof + '\'' +
                '}';
    }
} 