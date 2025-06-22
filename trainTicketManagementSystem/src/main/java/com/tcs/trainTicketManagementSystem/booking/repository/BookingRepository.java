package com.tcs.trainTicketManagementSystem.booking.repository;

import com.tcs.trainTicketManagementSystem.booking.model.Booking;
import com.tcs.trainTicketManagementSystem.booking.model.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Booking entity.
 */
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find bookings by user ID
    @Query("SELECT b FROM Booking b WHERE b.user.userId = :userId ORDER BY b.bookingDate DESC")
    List<Booking> findByUserIdOrderByBookingDateDesc(@Param("userId") Long userId);

    // Find bookings by user ID and status
    @Query("SELECT b FROM Booking b WHERE b.user.userId = :userId AND b.status = :status ORDER BY b.bookingDate DESC")
    List<Booking> findByUserIdAndStatusOrderByBookingDateDesc(@Param("userId") Long userId, @Param("status") BookingStatus status);

    // Find bookings by train ID
    @Query("SELECT b FROM Booking b WHERE b.train.trainId = :trainId ORDER BY b.bookingDate DESC")
    List<Booking> findByTrainIdOrderByBookingDateDesc(@Param("trainId") Long trainId);

    // Find bookings by fare type ID
    @Query("SELECT b FROM Booking b WHERE b.fareType.fareTypeId = :fareTypeId ORDER BY b.bookingDate DESC")
    List<Booking> findByFareTypeIdOrderByBookingDateDesc(@Param("fareTypeId") Long fareTypeId);

    // Find bookings by status
    List<Booking> findByStatusOrderByBookingDateDesc(BookingStatus status);

    // Find bookings by journey date
    List<Booking> findByJourneyDateOrderByBookingDateDesc(LocalDate journeyDate);

    // Find bookings by journey date range
    List<Booking> findByJourneyDateBetweenOrderByJourneyDateAsc(LocalDate fromDate, LocalDate toDate);

    // Find bookings by booking date range
    List<Booking> findByBookingDateBetweenOrderByBookingDateDesc(LocalDate fromDate, LocalDate toDate);

    // Find bookings by user ID and journey date
    @Query("SELECT b FROM Booking b WHERE b.user.userId = :userId AND b.journeyDate = :journeyDate ORDER BY b.bookingDate DESC")
    List<Booking> findByUserIdAndJourneyDateOrderByBookingDateDesc(@Param("userId") Long userId, @Param("journeyDate") LocalDate journeyDate);

    // Find bookings by user ID and journey date range
    @Query("SELECT b FROM Booking b WHERE b.user.userId = :userId AND b.journeyDate BETWEEN :fromDate AND :toDate ORDER BY b.journeyDate ASC")
    List<Booking> findByUserIdAndJourneyDateBetweenOrderByJourneyDateAsc(@Param("userId") Long userId, @Param("fromDate") LocalDate fromDate, @Param("toDate") LocalDate toDate);

    // Find upcoming bookings for a user (journey date >= today)
    @Query("SELECT b FROM Booking b WHERE b.user.userId = :userId AND b.journeyDate >= :today ORDER BY b.journeyDate ASC")
    List<Booking> findUpcomingBookingsByUserId(@Param("userId") Long userId, @Param("today") LocalDate today);

    // Find past bookings for a user (journey date < today)
    @Query("SELECT b FROM Booking b WHERE b.user.userId = :userId AND b.journeyDate < :today ORDER BY b.journeyDate DESC")
    List<Booking> findPastBookingsByUserId(@Param("userId") Long userId, @Param("today") LocalDate today);

    // Find bookings by source and destination (using train details)
    @Query("SELECT b FROM Booking b JOIN b.train t WHERE t.source = :source AND t.destination = :destination ORDER BY b.bookingDate DESC")
    List<Booking> findBySourceAndDestination(@Param("source") String source, @Param("destination") String destination);

    // Find bookings by train name (using train details)
    @Query("SELECT b FROM Booking b JOIN b.train t WHERE t.trainName LIKE %:trainName% ORDER BY b.bookingDate DESC")
    List<Booking> findByTrainNameContaining(@Param("trainName") String trainName);

    // Find bookings by username (using user details)
    @Query("SELECT b FROM Booking b JOIN b.user u WHERE u.username LIKE %:username% ORDER BY b.bookingDate DESC")
    List<Booking> findByUsernameContaining(@Param("username") String username);

    // Find bookings by source (using train details)
    @Query("SELECT b FROM Booking b JOIN b.train t WHERE t.source LIKE %:source% ORDER BY b.bookingDate DESC")
    List<Booking> findBySourceContaining(@Param("source") String source);

    // Find bookings by destination (using train details)
    @Query("SELECT b FROM Booking b JOIN b.train t WHERE t.destination LIKE %:destination% ORDER BY b.bookingDate DESC")
    List<Booking> findByDestinationContaining(@Param("destination") String destination);

    // Count bookings by user ID
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.user.userId = :userId")
    long countByUserId(@Param("userId") Long userId);

    // Count bookings by user ID and status
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.user.userId = :userId AND b.status = :status")
    long countByUserIdAndStatus(@Param("userId") Long userId, @Param("status") BookingStatus status);

    // Count bookings by train ID
    @Query("SELECT COUNT(b) FROM Booking b WHERE b.train.trainId = :trainId")
    long countByTrainId(@Param("trainId") Long trainId);

    // Count bookings by journey date
    long countByJourneyDate(LocalDate journeyDate);

    // Check if booking exists by ID
    boolean existsByBookingId(Long bookingId);

    // Check if booking exists by user ID and train ID and journey date
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Booking b WHERE b.user.userId = :userId AND b.train.trainId = :trainId AND b.journeyDate = :journeyDate")
    boolean existsByUserIdAndTrainIdAndJourneyDate(@Param("userId") Long userId, @Param("trainId") Long trainId, @Param("journeyDate") LocalDate journeyDate);
} 