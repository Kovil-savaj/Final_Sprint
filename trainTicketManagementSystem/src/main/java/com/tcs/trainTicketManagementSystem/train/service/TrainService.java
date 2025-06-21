package com.tcs.trainTicketManagementSystem.train.service;

import com.tcs.trainTicketManagementSystem.train.dto.TrainRequest;
import com.tcs.trainTicketManagementSystem.train.dto.TrainResponse;
import com.tcs.trainTicketManagementSystem.train.dto.TrainSearchRequest;
import com.tcs.trainTicketManagementSystem.train.model.TrainStatus;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for Train operations.
 */
public interface TrainService {

    /**
     * Create a new train with schedules and fare types.
     */
    TrainResponse createTrain(TrainRequest request);

    /**
     * Get train by ID.
     */
    TrainResponse getTrainById(Long trainId);

    /**
     * Get train by name.
     */
    TrainResponse getTrainByName(String trainName);

    /**
     * Get all trains.
     */
    List<TrainResponse> getAllTrains();

    /**
     * Get trains by status.
     */
    List<TrainResponse> getTrainsByStatus(TrainStatus status);

    /**
     * Get trains by source and destination.
     */
    List<TrainResponse> getTrainsByRoute(String source, String destination);

    /**
     * Get trains by source.
     */
    List<TrainResponse> getTrainsBySource(String source);

    /**
     * Get trains by destination.
     */
    List<TrainResponse> getTrainsByDestination(String destination);

    /**
     * Search trains by name pattern.
     */
    List<TrainResponse> searchTrainsByName(String trainName);

    /**
     * Search trains by source pattern.
     */
    List<TrainResponse> searchTrainsBySource(String source);

    /**
     * Search trains by destination pattern.
     */
    List<TrainResponse> searchTrainsByDestination(String destination);

    /**
     * Get trains by schedule day.
     */
    List<TrainResponse> getTrainsByScheduleDay(String dayOfWeek);

    /**
     * Get trains by schedule day and route.
     */
    List<TrainResponse> getTrainsByScheduleDayAndRoute(String dayOfWeek, String source, String destination);

    /**
     * Get trains with available seats.
     */
    List<TrainResponse> getTrainsWithAvailableSeats();

    /**
     * Get trains with available seats for route.
     */
    List<TrainResponse> getTrainsWithAvailableSeatsForRoute(String source, String destination);

    /**
     * Search trains with various criteria.
     */
    List<TrainResponse> searchTrains(TrainSearchRequest searchRequest);

    /**
     * Update train information.
     */
    TrainResponse updateTrain(Long trainId, TrainRequest request);

    /**
     * Update train status.
     */
    TrainResponse updateTrainStatus(Long trainId, TrainStatus status);

    /**
     * Delete train by ID.
     */
    void deleteTrain(Long trainId);

    /**
     * Check if train exists by name.
     */
    boolean existsByTrainName(String trainName);

    /**
     * Check if train exists by route.
     */
    boolean existsByRoute(String source, String destination);

    /**
     * Get available trains for a specific journey date.
     */
    List<TrainResponse> getAvailableTrainsForDate(String source, String destination, LocalDate journeyDate);
} 