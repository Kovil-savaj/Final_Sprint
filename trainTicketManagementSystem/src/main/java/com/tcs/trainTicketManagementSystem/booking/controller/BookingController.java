package com.tcs.trainTicketManagementSystem.booking.controller;

import com.tcs.trainTicketManagementSystem.booking.dto.*;
import com.tcs.trainTicketManagementSystem.booking.model.BookingStatus;
import com.tcs.trainTicketManagementSystem.booking.service.BookingService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Booking and Passenger operations.
 */
@RestController
@RequestMapping("/api/v1/bookings")
@CrossOrigin(origins = "*")
@Tag(name = "Booking Management", description = "APIs for managing bookings and passengers")
public class BookingController {

    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    private BookingService bookingService;

    // Booking Endpoints

    /**
     * Create a new booking with passengers.
     */
    @PostMapping
    @Operation(summary = "Create a new booking", description = "Creates a new booking with passengers for a train journey. Validates seat availability, user existence, and passenger details.")
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        logger.info("Creating booking for user: {}, train: {}", request.getUserId(), request.getTrainId());
        
        BookingResponse response = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get booking by ID.
     */
    @GetMapping("/{bookingId}")
    @Operation(summary = "Get booking by ID", description = "Retrieves complete booking information including passenger details by booking ID.")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long bookingId) {
        logger.info("Getting booking by ID: {}", bookingId);
        
        BookingResponse response = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all bookings.
     */
    @GetMapping
    @Operation(summary = "Get all bookings", description = "Retrieves all bookings in the system with complete details including passengers.")
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        logger.info("Getting all bookings");
        
        List<BookingResponse> response = bookingService.getAllBookings();
        return ResponseEntity.ok(response);
    }

    /**
     * Get bookings by user ID.
     */
    @GetMapping("/user/{userId}")
    @Operation(summary = "Get bookings by user ID", description = "Retrieves all bookings for a specific user with complete booking and passenger details.")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserId(@PathVariable Long userId) {
        logger.info("Getting bookings for user ID: {}", userId);
        
        List<BookingResponse> response = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get bookings by user ID and status.
     */
    @GetMapping("/user/{userId}/status/{status}")
    @Operation(summary = "Get bookings by user ID and status", description = "Retrieves bookings for a specific user filtered by booking status (CONFIRMED or CANCELLED).")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserIdAndStatus(
            @PathVariable Long userId, @PathVariable String status) {
        logger.info("Getting bookings for user ID: {} with status: {}", userId, status);
        
        BookingStatus bookingStatus = BookingStatus.valueOf(status.toUpperCase());
        List<BookingResponse> response = bookingService.getBookingsByUserIdAndStatus(userId, bookingStatus);
        return ResponseEntity.ok(response);
    }

    /**
     * Get upcoming bookings for a user.
     */
    @GetMapping("/user/{userId}/upcoming")
    @Operation(summary = "Get upcoming bookings for user", description = "Retrieves all future bookings (journey date >= today) for a specific user.")
    public ResponseEntity<List<BookingResponse>> getUpcomingBookingsByUserId(@PathVariable Long userId) {
        logger.info("Getting upcoming bookings for user ID: {}", userId);
        
        List<BookingResponse> response = bookingService.getUpcomingBookingsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get past bookings for a user.
     */
    @GetMapping("/user/{userId}/past")
    @Operation(summary = "Get past bookings for user", description = "Retrieves all past bookings (journey date < today) for a specific user.")
    public ResponseEntity<List<BookingResponse>> getPastBookingsByUserId(@PathVariable Long userId) {
        logger.info("Getting past bookings for user ID: {}", userId);
        
        List<BookingResponse> response = bookingService.getPastBookingsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Cancel booking.
     */
    @PutMapping("/{bookingId}/cancel")
    @Operation(summary = "Cancel booking", description = "Cancels a booking and updates the status to CANCELLED. Automatically restores seat availability.")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long bookingId) {
        logger.info("Cancelling booking with ID: {}", bookingId);
        
        BookingResponse response = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(response);
    }

    /**
     * Check if booking exists by ID.
     */
    @GetMapping("/exists/{bookingId}")
    @Operation(summary = "Check if booking exists", description = "Checks if a booking with the given ID exists in the system. Returns true if exists, false otherwise.")
    public ResponseEntity<Boolean> existsByBookingId(@PathVariable Long bookingId) {
        logger.info("Checking if booking exists with ID: {}", bookingId);
        
        boolean exists = bookingService.existsByBookingId(bookingId);
        return ResponseEntity.ok(exists);
    }

    // Passenger Endpoints

    /**
     * Get passengers by booking ID.
     */
    @GetMapping("/{bookingId}/passengers")
    @Operation(summary = "Get passengers by booking ID", description = "Retrieves all passengers associated with a specific booking.")
    public ResponseEntity<List<PassengerResponse>> getPassengersByBookingId(@PathVariable Long bookingId) {
        logger.info("Getting passengers for booking ID: {}", bookingId);
        
        List<PassengerResponse> response = bookingService.getPassengersByBookingId(bookingId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get passenger by ID.
     */
    @GetMapping("/passengers/{passengerId}")
    @Operation(summary = "Get passenger by ID", description = "Retrieves passenger information by passenger ID.")
    public ResponseEntity<PassengerResponse> getPassengerById(@PathVariable Long passengerId) {
        logger.info("Getting passenger by ID: {}", passengerId);
        
        PassengerResponse response = bookingService.getPassengerById(passengerId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get passenger by ID proof.
     */
    @GetMapping("/passengers/id-proof/{idProof}")
    @Operation(summary = "Get passenger by ID proof", description = "Retrieves passenger information by Aadhar card number (12-digit ID proof).")
    public ResponseEntity<PassengerResponse> getPassengerByIdProof(@PathVariable String idProof) {
        logger.info("Getting passenger by ID proof: {}", idProof);
        
        PassengerResponse response = bookingService.getPassengerByIdProof(idProof);
        return ResponseEntity.ok(response);
    }

    /**
     * Add passenger to existing booking.
     */
    @PostMapping("/{bookingId}/passengers")
    @Operation(summary = "Add passenger to booking", description = "Adds a new passenger to an existing booking. Validates passenger details and ensures booking is not cancelled.")
    public ResponseEntity<PassengerResponse> addPassengerToBooking(
            @PathVariable Long bookingId, @Valid @RequestBody PassengerRequest passengerRequest) {
        logger.info("Adding passenger to booking ID: {}", bookingId);
        
        PassengerResponse response = bookingService.addPassengerToBooking(bookingId, passengerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Update passenger information.
     */
    @PutMapping("/passengers/{passengerId}")
    @Operation(summary = "Update passenger information", description = "Updates passenger information. Validates input data and ensures the booking is not cancelled.")
    public ResponseEntity<PassengerResponse> updatePassenger(
            @PathVariable Long passengerId, @Valid @RequestBody PassengerRequest passengerRequest) {
        logger.info("Updating passenger with ID: {}", passengerId);
        
        PassengerResponse response = bookingService.updatePassenger(passengerId, passengerRequest);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete passenger by ID.
     */
    @DeleteMapping("/passengers/{passengerId}")
    @Operation(summary = "Delete passenger", description = "Deletes a passenger from a booking. Ensures the booking is not cancelled before deletion.")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long passengerId) {
        logger.info("Deleting passenger with ID: {}", passengerId);
        
        bookingService.deletePassenger(passengerId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Check if passenger exists by ID proof.
     */
    @GetMapping("/passengers/exists/id-proof/{idProof}")
    @Operation(summary = "Check if passenger exists by ID proof", description = "Checks if a passenger with the given Aadhar card number exists in the system. Returns true if exists, false otherwise.")
    public ResponseEntity<Boolean> existsPassengerByIdProof(@PathVariable String idProof) {
        logger.info("Checking if passenger exists with ID proof: {}", idProof);
        
        boolean exists = bookingService.existsPassengerByIdProof(idProof);
        return ResponseEntity.ok(exists);
    }
} 