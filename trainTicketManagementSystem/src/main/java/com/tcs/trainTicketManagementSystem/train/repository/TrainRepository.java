package com.tcs.trainTicketManagementSystem.train.repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tcs.trainTicketManagementSystem.train.model.DayOfWeek;
import com.tcs.trainTicketManagementSystem.train.model.Train;
import com.tcs.trainTicketManagementSystem.train.model.TrainStatus;

/**
 * Repository interface for Train entity operations.
 */
@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {

    /**
     * Find train by name (case-insensitive).
     */
    Optional<Train> findByTrainNameIgnoreCase(String trainName);

    /**
     * Find train by name (case-sensitive - for backward compatibility).
     */
    Optional<Train> findByTrainName(String trainName);

    /**
     * Find trains by status.
     */
    List<Train> findByStatus(TrainStatus status);

    /**
     * Find trains by source and destination (case-insensitive).
     */
    List<Train> findBySourceIgnoreCaseAndDestinationIgnoreCase(String source, String destination);

    /**
     * Find trains by source and destination (case-sensitive - for backward
     * compatibility).
     */
    List<Train> findBySourceAndDestination(String source, String destination);

    /**
     * Find trains by source (case-insensitive).
     */
    List<Train> findBySourceIgnoreCase(String source);

    /**
     * Find trains by source (case-sensitive - for backward compatibility).
     */
    List<Train> findBySource(String source);

    /**
     * Find trains by destination (case-insensitive).
     */
    List<Train> findByDestinationIgnoreCase(String destination);

    /**
     * Find trains by destination (case-sensitive - for backward compatibility).
     */
    List<Train> findByDestination(String destination);

    /**
     * Find trains by source, destination, and status (case-insensitive).
     */
    List<Train> findBySourceIgnoreCaseAndDestinationIgnoreCaseAndStatus(String source, String destination, TrainStatus status);

    /**
     * Find trains by source, destination, and status (case-sensitive - for
     * backward compatibility).
     */
    List<Train> findBySourceAndDestinationAndStatus(String source, String destination, TrainStatus status);

    /**
     * Find trains by departure time range.
     */
    List<Train> findByDepartureTimeBetween(LocalTime startTime, LocalTime endTime);

    /**
     * Search trains by name containing pattern (case-insensitive).
     */
    List<Train> findByTrainNameContainingIgnoreCase(String trainName);

    /**
     * Search trains by source containing pattern (case-insensitive).
     */
    List<Train> findBySourceContainingIgnoreCase(String source);

    /**
     * Search trains by destination containing pattern (case-insensitive).
     */
    List<Train> findByDestinationContainingIgnoreCase(String destination);

    /**
     * Check if train exists by name (case-insensitive).
     */
    boolean existsByTrainNameIgnoreCase(String trainName);

    /**
     * Check if train exists by name (case-sensitive - for backward
     * compatibility).
     */
    boolean existsByTrainName(String trainName);

    /**
     * Check if train exists by source and destination (case-insensitive).
     */
    boolean existsBySourceIgnoreCaseAndDestinationIgnoreCase(String source, String destination);

    /**
     * Check if train exists by source and destination (case-sensitive - for
     * backward compatibility).
     */
    boolean existsBySourceAndDestination(String source, String destination);

    /**
     * Find trains with schedules for specific day of week.
     */
    @Query("SELECT DISTINCT t FROM Train t JOIN t.schedules s WHERE s.dayOfWeek = :dayOfWeek")
    List<Train> findTrainsByScheduleDay(@Param("dayOfWeek") DayOfWeek dayOfWeek);

    /**
     * Find trains with schedules for specific day and route (case-insensitive).
     */
    @Query("SELECT DISTINCT t FROM Train t JOIN t.schedules s WHERE s.dayOfWeek = :dayOfWeek AND LOWER(t.source) = LOWER(:source) AND LOWER(t.destination) = LOWER(:destination)")
    List<Train> findTrainsByScheduleDayAndRoute(@Param("dayOfWeek") DayOfWeek dayOfWeek,
            @Param("source") String source,
            @Param("destination") String destination);

    /**
     * Find trains with available seats.
     */
    @Query("SELECT DISTINCT t FROM Train t JOIN t.fareTypes f WHERE f.seatsAvailable > 0")
    List<Train> findTrainsWithAvailableSeats();

    /**
     * Find trains with available seats for specific route (case-insensitive).
     */
    @Query("SELECT DISTINCT t FROM Train t JOIN t.fareTypes f WHERE f.seatsAvailable > 0 AND LOWER(t.source) = LOWER(:source) AND LOWER(t.destination) = LOWER(:destination)")
    List<Train> findTrainsWithAvailableSeatsForRoute(@Param("source") String source,
            @Param("destination") String destination);

    /**
     * Get all distinct source stations.
     */
    @Query("SELECT DISTINCT t.source FROM Train t ORDER BY t.source")
    List<String> findDistinctSourceStations();

    /**
     * Get all distinct destination stations.
     */
    @Query("SELECT DISTINCT t.destination FROM Train t ORDER BY t.destination")
    List<String> findDistinctDestinationStations();

    /**
     * Count trains by status.
     */
    long countByStatus(TrainStatus status);
}
