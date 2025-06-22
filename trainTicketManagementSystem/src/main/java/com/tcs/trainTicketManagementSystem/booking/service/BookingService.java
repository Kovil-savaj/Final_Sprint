package com.tcs.trainTicketManagementSystem.booking.service;

import com.tcs.trainTicketManagementSystem.booking.dto.BookingRequest;
import com.tcs.trainTicketManagementSystem.booking.dto.BookingResponse;
import com.tcs.trainTicketManagementSystem.booking.dto.BookingSearchRequest;
import com.tcs.trainTicketManagementSystem.booking.dto.PassengerRequest;
import com.tcs.trainTicketManagementSystem.booking.dto.PassengerResponse;
import com.tcs.trainTicketManagementSystem.booking.model.BookingStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for Booking and Passenger operations.
 */
public interface BookingService {

    // Booking Operations

    /**
     * Create a new booking with passengers.
     */
    BookingResponse createBooking(BookingRequest request);

    /**
     * Get booking by ID.
     */
    BookingResponse getBookingById(Long bookingId);

    /**
     * Get all bookings.
     */
    List<BookingResponse> getAllBookings();

    /**
     * Get bookings by user ID.
     */
    List<BookingResponse> getBookingsByUserId(Long userId);

    /**
     * Get bookings by user ID and status.
     */
    List<BookingResponse> getBookingsByUserIdAndStatus(Long userId, BookingStatus status);

    /**
     * Get upcoming bookings for a user.
     */
    List<BookingResponse> getUpcomingBookingsByUserId(Long userId);

    /**
     * Get past bookings for a user.
     */
    List<BookingResponse> getPastBookingsByUserId(Long userId);

    /**
     * Get bookings by train ID.
     */
    List<BookingResponse> getBookingsByTrainId(Long trainId);

    /**
     * Get bookings by fare type ID.
     */
    List<BookingResponse> getBookingsByFareTypeId(Long fareTypeId);

    /**
     * Get bookings by status.
     */
    List<BookingResponse> getBookingsByStatus(BookingStatus status);

    /**
     * Get bookings by journey date.
     */
    List<BookingResponse> getBookingsByJourneyDate(LocalDate journeyDate);

    /**
     * Get bookings by journey date range.
     */
    List<BookingResponse> getBookingsByJourneyDateRange(LocalDate fromDate, LocalDate toDate);

    /**
     * Get bookings by booking date range.
     */
    List<BookingResponse> getBookingsByBookingDateRange(LocalDate fromDate, LocalDate toDate);

    /**
     * Get bookings by source and destination.
     */
    List<BookingResponse> getBookingsByRoute(String source, String destination);

    /**
     * Search bookings by train name.
     */
    List<BookingResponse> searchBookingsByTrainName(String trainName);

    /**
     * Search bookings by username.
     */
    List<BookingResponse> searchBookingsByUsername(String username);

    /**
     * Search bookings by source.
     */
    List<BookingResponse> searchBookingsBySource(String source);

    /**
     * Search bookings by destination.
     */
    List<BookingResponse> searchBookingsByDestination(String destination);

    /**
     * Search bookings with various criteria.
     */
    List<BookingResponse> searchBookings(BookingSearchRequest searchRequest);

    /**
     * Update booking status.
     */
    BookingResponse updateBookingStatus(Long bookingId, BookingStatus status);

    /**
     * Cancel booking.
     */
    BookingResponse cancelBooking(Long bookingId);

    /**
     * Delete booking by ID.
     */
    void deleteBooking(Long bookingId);

    /**
     * Check if booking exists by ID.
     */
    boolean existsByBookingId(Long bookingId);

    /**
     * Check if booking exists by user ID, train ID, and journey date.
     */
    boolean existsByUserIdAndTrainIdAndJourneyDate(Long userId, Long trainId, LocalDate journeyDate);

    /**
     * Get booking statistics for a user.
     */
    BookingStatistics getBookingStatisticsByUserId(Long userId);

    // Passenger Operations

    /**
     * Get passengers by booking ID.
     */
    List<PassengerResponse> getPassengersByBookingId(Long bookingId);

    /**
     * Get passenger by ID.
     */
    PassengerResponse getPassengerById(Long passengerId);

    /**
     * Get passenger by ID proof (Aadhar card number).
     */
    PassengerResponse getPassengerByIdProof(String idProof);

    /**
     * Search passengers by name.
     */
    List<PassengerResponse> searchPassengersByName(String name);

    /**
     * Get passengers by age range.
     */
    List<PassengerResponse> getPassengersByAgeRange(Integer minAge, Integer maxAge);

    /**
     * Get passengers by gender.
     */
    List<PassengerResponse> getPassengersByGender(String gender);

    /**
     * Search passengers by ID proof pattern.
     */
    List<PassengerResponse> searchPassengersByIdProof(String idProof);

    /**
     * Add passenger to existing booking.
     */
    PassengerResponse addPassengerToBooking(Long bookingId, PassengerRequest passengerRequest);

    /**
     * Update passenger information.
     */
    PassengerResponse updatePassenger(Long passengerId, PassengerRequest passengerRequest);

    /**
     * Delete passenger by ID.
     */
    void deletePassenger(Long passengerId);

    /**
     * Check if passenger exists by ID proof.
     */
    boolean existsPassengerByIdProof(String idProof);

    /**
     * Check if passenger exists by booking ID and ID proof.
     */
    boolean existsPassengerByBookingIdAndIdProof(Long bookingId, String idProof);

    /**
     * Get passenger statistics.
     */
    PassengerStatistics getPassengerStatistics();

    /**
     * Booking statistics class.
     */
    class BookingStatistics {
        private long totalBookings;
        private long confirmedBookings;
        private long cancelledBookings;
        private long upcomingBookings;
        private long pastBookings;

        public BookingStatistics(long totalBookings, long confirmedBookings, long cancelledBookings, 
                                long upcomingBookings, long pastBookings) {
            this.totalBookings = totalBookings;
            this.confirmedBookings = confirmedBookings;
            this.cancelledBookings = cancelledBookings;
            this.upcomingBookings = upcomingBookings;
            this.pastBookings = pastBookings;
        }

        // Getters
        public long getTotalBookings() { return totalBookings; }
        public long getConfirmedBookings() { return confirmedBookings; }
        public long getCancelledBookings() { return cancelledBookings; }
        public long getUpcomingBookings() { return upcomingBookings; }
        public long getPastBookings() { return pastBookings; }
    }

    /**
     * Passenger statistics class.
     */
    class PassengerStatistics {
        private long totalPassengers;
        private long malePassengers;
        private long femalePassengers;
        private long otherPassengers;
        private double averageAge;

        public PassengerStatistics(long totalPassengers, long malePassengers, long femalePassengers, 
                                  long otherPassengers, double averageAge) {
            this.totalPassengers = totalPassengers;
            this.malePassengers = malePassengers;
            this.femalePassengers = femalePassengers;
            this.otherPassengers = otherPassengers;
            this.averageAge = averageAge;
        }

        // Getters
        public long getTotalPassengers() { return totalPassengers; }
        public long getMalePassengers() { return malePassengers; }
        public long getFemalePassengers() { return femalePassengers; }
        public long getOtherPassengers() { return otherPassengers; }
        public double getAverageAge() { return averageAge; }
    }
} 