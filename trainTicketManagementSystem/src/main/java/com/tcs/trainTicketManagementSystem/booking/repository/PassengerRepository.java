package com.tcs.trainTicketManagementSystem.booking.repository;

import com.tcs.trainTicketManagementSystem.booking.model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Passenger entity.
 */
@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    // Find passengers by booking ID
    List<Passenger> findByBooking_BookingIdOrderByPassengerId(Long bookingId);

    // Find passenger by ID proof (Aadhar card number)
    Optional<Passenger> findByIdProof(String idProof);

    // Find passengers by name (partial match)
    List<Passenger> findByNameContainingIgnoreCaseOrderByName(String name);

    // Find passengers by age range
    List<Passenger> findByAgeBetweenOrderByAge(Integer minAge, Integer maxAge);

    // Find passengers by gender
    List<Passenger> findByGenderOrderByName(String gender);

    // Find passengers by booking ID and gender
    List<Passenger> findByBooking_BookingIdAndGenderOrderByName(Long bookingId, String gender);

    // Find passengers by age greater than or equal to
    List<Passenger> findByAgeGreaterThanEqualOrderByAge(Integer age);

    // Find passengers by age less than or equal to
    List<Passenger> findByAgeLessThanEqualOrderByAge(Integer age);

    // Count passengers by booking ID
    long countByBooking_BookingId(Long bookingId);

    // Count passengers by gender
    long countByGender(String gender);

    // Count passengers by age range
    long countByAgeBetween(Integer minAge, Integer maxAge);

    // Check if passenger exists by ID proof
    boolean existsByIdProof(String idProof);

    // Check if passenger exists by booking ID and ID proof
    boolean existsByBooking_BookingIdAndIdProof(Long bookingId, String idProof);

    // Find passengers by booking ID with booking details (using JOIN)
    @Query("SELECT p FROM Passenger p JOIN FETCH p.booking b WHERE b.bookingId = :bookingId ORDER BY p.passengerId")
    List<Passenger> findByBookingIdWithBookingDetails(@Param("bookingId") Long bookingId);

    // Find passengers by name pattern across all bookings
    @Query("SELECT p FROM Passenger p JOIN FETCH p.booking b WHERE p.name LIKE %:name% ORDER BY p.name")
    List<Passenger> findByNamePatternWithBookingDetails(@Param("name") String name);

    // Find passengers by ID proof pattern
    @Query("SELECT p FROM Passenger p WHERE p.idProof LIKE %:idProof% ORDER BY p.idProof")
    List<Passenger> findByIdProofContaining(@Param("idProof") String idProof);
} 