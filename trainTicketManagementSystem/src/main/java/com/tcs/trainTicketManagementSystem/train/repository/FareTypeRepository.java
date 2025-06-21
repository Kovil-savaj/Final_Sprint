package com.tcs.trainTicketManagementSystem.train.repository;

import com.tcs.trainTicketManagementSystem.train.model.FareType;
import com.tcs.trainTicketManagementSystem.train.model.ClassType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for FareType entity operations.
 */
@Repository
public interface FareTypeRepository extends JpaRepository<FareType, Long> {

    /**
     * Find fare types by train ID.
     */
    List<FareType> findByTrainTrainId(Long trainId);

    /**
     * Find fare type by train ID and class type.
     */
    Optional<FareType> findByTrainTrainIdAndClassType(Long trainId, ClassType classType);

    /**
     * Find fare types by class type.
     */
    List<FareType> findByClassType(ClassType classType);

    /**
     * Find fare types with available seats.
     */
    List<FareType> findBySeatsAvailableGreaterThan(Integer seats);

    /**
     * Find fare types by train ID with available seats.
     */
    List<FareType> findByTrainTrainIdAndSeatsAvailableGreaterThan(Long trainId, Integer seats);

    /**
     * Find fare types by price range.
     */
    List<FareType> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Find fare types by train ID and price range.
     */
    List<FareType> findByTrainTrainIdAndPriceBetween(Long trainId, BigDecimal minPrice, BigDecimal maxPrice);

    /**
     * Find fare types with minimum seats available.
     */
    @Query("SELECT f FROM FareType f WHERE f.seatsAvailable >= :minSeats")
    List<FareType> findFareTypesWithMinimumSeats(@Param("minSeats") Integer minSeats);

    /**
     * Find fare types for specific route with available seats.
     */
    @Query("SELECT f FROM FareType f JOIN f.train t WHERE t.source = :source AND t.destination = :destination AND f.seatsAvailable > 0")
    List<FareType> findFareTypesForRouteWithAvailableSeats(@Param("source") String source, 
                                                          @Param("destination") String destination);

    /**
     * Check if fare type exists for train and class.
     */
    boolean existsByTrainTrainIdAndClassType(Long trainId, ClassType classType);

    /**
     * Delete fare types by train ID.
     */
    void deleteByTrainTrainId(Long trainId);
} 