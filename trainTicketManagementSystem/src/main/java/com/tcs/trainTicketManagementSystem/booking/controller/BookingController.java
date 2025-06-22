package com.tcs.trainTicketManagementSystem.booking.controller;

import com.tcs.trainTicketManagementSystem.booking.dto.*;
import com.tcs.trainTicketManagementSystem.booking.model.BookingStatus;
import com.tcs.trainTicketManagementSystem.booking.service.BookingService;

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
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request) {
        logger.info("Creating booking for user: {}, train: {}", request.getUserId(), request.getTrainId());
        
        BookingResponse response = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get booking by ID.
     */
    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable Long bookingId) {
        logger.info("Getting booking by ID: {}", bookingId);
        
        BookingResponse response = bookingService.getBookingById(bookingId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all bookings.
     */
    @GetMapping
    public ResponseEntity<List<BookingResponse>> getAllBookings() {
        logger.info("Getting all bookings");
        
        List<BookingResponse> response = bookingService.getAllBookings();
        return ResponseEntity.ok(response);
    }

    /**
     * Get bookings by user ID.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getBookingsByUserId(@PathVariable Long userId) {
        logger.info("Getting bookings for user ID: {}", userId);
        
        List<BookingResponse> response = bookingService.getBookingsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get bookings by user ID and status.
     */
    @GetMapping("/user/{userId}/status/{status}")
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
    public ResponseEntity<List<BookingResponse>> getUpcomingBookingsByUserId(@PathVariable Long userId) {
        logger.info("Getting upcoming bookings for user ID: {}", userId);
        
        List<BookingResponse> response = bookingService.getUpcomingBookingsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get past bookings for a user.
     */
    @GetMapping("/user/{userId}/past")
    public ResponseEntity<List<BookingResponse>> getPastBookingsByUserId(@PathVariable Long userId) {
        logger.info("Getting past bookings for user ID: {}", userId);
        
        List<BookingResponse> response = bookingService.getPastBookingsByUserId(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Cancel booking.
     */
    @PutMapping("/{bookingId}/cancel")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long bookingId) {
        logger.info("Cancelling booking with ID: {}", bookingId);
        
        BookingResponse response = bookingService.cancelBooking(bookingId);
        return ResponseEntity.ok(response);
    }

    /**
     * Check if booking exists by ID.
     */
    @GetMapping("/exists/{bookingId}")
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
    public ResponseEntity<List<PassengerResponse>> getPassengersByBookingId(@PathVariable Long bookingId) {
        logger.info("Getting passengers for booking ID: {}", bookingId);
        
        List<PassengerResponse> response = bookingService.getPassengersByBookingId(bookingId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get passenger by ID.
     */
    @GetMapping("/passengers/{passengerId}")
    public ResponseEntity<PassengerResponse> getPassengerById(@PathVariable Long passengerId) {
        logger.info("Getting passenger by ID: {}", passengerId);
        
        PassengerResponse response = bookingService.getPassengerById(passengerId);
        return ResponseEntity.ok(response);
    }

    /**
     * Get passenger by ID proof.
     */
    @GetMapping("/passengers/id-proof/{idProof}")
    public ResponseEntity<PassengerResponse> getPassengerByIdProof(@PathVariable String idProof) {
        logger.info("Getting passenger by ID proof: {}", idProof);
        
        PassengerResponse response = bookingService.getPassengerByIdProof(idProof);
        return ResponseEntity.ok(response);
    }

    /**
     * Add passenger to existing booking.
     */
    @PostMapping("/{bookingId}/passengers")
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
    public ResponseEntity<Void> deletePassenger(@PathVariable Long passengerId) {
        logger.info("Deleting passenger with ID: {}", passengerId);
        
        bookingService.deletePassenger(passengerId);
        return ResponseEntity.noContent().build();
    }

    /**
     * Check if passenger exists by ID proof.
     */
    @GetMapping("/passengers/exists/id-proof/{idProof}")
    public ResponseEntity<Boolean> existsPassengerByIdProof(@PathVariable String idProof) {
        logger.info("Checking if passenger exists with ID proof: {}", idProof);
        
        boolean exists = bookingService.existsPassengerByIdProof(idProof);
        return ResponseEntity.ok(exists);
    }
} 