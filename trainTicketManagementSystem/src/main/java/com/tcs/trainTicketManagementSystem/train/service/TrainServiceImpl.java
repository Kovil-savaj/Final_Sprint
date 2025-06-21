package com.tcs.trainTicketManagementSystem.train.service;

import com.tcs.trainTicketManagementSystem.train.dto.TrainRequest;
import com.tcs.trainTicketManagementSystem.train.dto.TrainResponse;
import com.tcs.trainTicketManagementSystem.train.dto.TrainSearchRequest;
import com.tcs.trainTicketManagementSystem.train.exception.TrainAlreadyExistsException;
import com.tcs.trainTicketManagementSystem.train.exception.TrainNotFoundException;
import com.tcs.trainTicketManagementSystem.train.model.*;
import com.tcs.trainTicketManagementSystem.train.repository.FareTypeRepository;
import com.tcs.trainTicketManagementSystem.train.repository.TrainRepository;
import com.tcs.trainTicketManagementSystem.train.repository.TrainScheduleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service implementation for Train operations.
 */
@Service
@Transactional
public class TrainServiceImpl implements TrainService {

    private static final Logger logger = LoggerFactory.getLogger(TrainServiceImpl.class);

    private final TrainRepository trainRepository;
    private final TrainScheduleRepository scheduleRepository;
    private final FareTypeRepository fareTypeRepository;

    public TrainServiceImpl(TrainRepository trainRepository, 
                           TrainScheduleRepository scheduleRepository,
                           FareTypeRepository fareTypeRepository) {
        this.trainRepository = trainRepository;
        this.scheduleRepository = scheduleRepository;
        this.fareTypeRepository = fareTypeRepository;
    }

    @Override
    public TrainResponse createTrain(TrainRequest request) {
        logger.info("Creating new train: {}", request.getTrainName());

        // Check if train already exists (case-insensitive)
        if (trainRepository.existsByTrainNameIgnoreCase(request.getTrainName())) {
            throw TrainAlreadyExistsException.withName(request.getTrainName());
        }

        // Validate arrival time is after departure time
        if (request.getArrivalTime().isBefore(request.getDepartureTime())) {
            throw new IllegalArgumentException("Arrival time must be after departure time");
        }

        // Create train entity
        Train train = new Train(
            request.getTrainName(),
            request.getSource(),
            request.getDestination(),
            request.getDepartureTime(),
            request.getArrivalTime(),
            request.getStatus()
        );

        Train savedTrain = trainRepository.save(train);

        // Create schedules if provided
        if (request.getScheduleDays() != null && !request.getScheduleDays().isEmpty()) {
            createSchedules(savedTrain, request.getScheduleDays());
        }

        // Create fare types if provided
        if (request.getFareTypes() != null && !request.getFareTypes().isEmpty()) {
            createFareTypes(savedTrain, request.getFareTypes());
        }

        logger.info("Train created successfully with ID: {}", savedTrain.getTrainId());
        return new TrainResponse(savedTrain);
    }

    @Override
    @Transactional(readOnly = true)
    public TrainResponse getTrainById(Long trainId) {
        logger.debug("Fetching train by ID: {}", trainId);
        
        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> TrainNotFoundException.withId(trainId));
        
        return new TrainResponse(train);
    }

    @Override
    @Transactional(readOnly = true)
    public TrainResponse getTrainByName(String trainName) {
        logger.debug("Fetching train by name: {}", trainName);
        
        Train train = trainRepository.findByTrainNameIgnoreCase(trainName)
                .orElseThrow(() -> TrainNotFoundException.withName(trainName));
        
        return new TrainResponse(train);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainResponse> getAllTrains() {
        logger.debug("Fetching all trains");
        
        return trainRepository.findAll().stream()
                .map(TrainResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainResponse> getTrainsByStatus(TrainStatus status) {
        logger.debug("Fetching trains by status: {}", status);
        
        return trainRepository.findByStatus(status).stream()
                .map(TrainResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainResponse> getTrainsByRoute(String source, String destination) {
        logger.debug("Fetching trains for route: {} to {}", source, destination);
        
        return trainRepository.findBySourceIgnoreCaseAndDestinationIgnoreCase(source, destination).stream()
                .map(TrainResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainResponse> getTrainsBySource(String source) {
        logger.debug("Fetching trains by source: {}", source);
        
        return trainRepository.findBySourceIgnoreCase(source).stream()
                .map(TrainResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainResponse> getTrainsByDestination(String destination) {
        logger.debug("Fetching trains by destination: {}", destination);
        
        return trainRepository.findByDestinationIgnoreCase(destination).stream()
                .map(TrainResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainResponse> searchTrainsByName(String trainName) {
        logger.debug("Searching trains by name pattern: {}", trainName);
        
        return trainRepository.findByTrainNameContainingIgnoreCase(trainName).stream()
                .map(TrainResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainResponse> searchTrainsBySource(String source) {
        logger.debug("Searching trains by source pattern: {}", source);
        
        return trainRepository.findBySourceContainingIgnoreCase(source).stream()
                .map(TrainResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainResponse> searchTrainsByDestination(String destination) {
        logger.debug("Searching trains by destination pattern: {}", destination);
        
        return trainRepository.findByDestinationContainingIgnoreCase(destination).stream()
                .map(TrainResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainResponse> getTrainsByScheduleDay(String dayOfWeek) {
        logger.debug("Fetching trains by schedule day: {}", dayOfWeek);
        
        try {
            DayOfWeek dayOfWeekEnum = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
            return trainRepository.findTrainsByScheduleDay(dayOfWeekEnum).stream()
                    .map(TrainResponse::new)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid day of week: {}", dayOfWeek);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainResponse> getTrainsByScheduleDayAndRoute(String dayOfWeek, String source, String destination) {
        logger.debug("Fetching trains by schedule day: {} and route: {} to {}", dayOfWeek, source, destination);
        
        try {
            DayOfWeek dayOfWeekEnum = DayOfWeek.valueOf(dayOfWeek.toUpperCase());
            return trainRepository.findTrainsByScheduleDayAndRoute(dayOfWeekEnum, source, destination).stream()
                    .map(TrainResponse::new)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid day of week: {}", dayOfWeek);
            return new ArrayList<>();
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainResponse> getTrainsWithAvailableSeats() {
        logger.debug("Fetching trains with available seats");
        
        return trainRepository.findTrainsWithAvailableSeats().stream()
                .map(TrainResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainResponse> getTrainsWithAvailableSeatsForRoute(String source, String destination) {
        logger.debug("Fetching trains with available seats for route: {} to {}", source, destination);
        
        return trainRepository.findTrainsWithAvailableSeatsForRoute(source, destination).stream()
                .map(TrainResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainResponse> searchTrains(TrainSearchRequest searchRequest) {
        logger.debug("Searching trains with criteria: {}", searchRequest);
        
        List<Train> trains = new ArrayList<>();
        
        // Apply filters based on search criteria (case-insensitive)
        if (searchRequest.getSource() != null && searchRequest.getDestination() != null) {
            trains = trainRepository.findBySourceIgnoreCaseAndDestinationIgnoreCaseAndStatus(
                searchRequest.getSource(), 
                searchRequest.getDestination(), 
                searchRequest.getStatus() != null ? searchRequest.getStatus() : TrainStatus.ACTIVE
            );
        } else if (searchRequest.getSource() != null) {
            trains = trainRepository.findBySourceIgnoreCase(searchRequest.getSource());
        } else if (searchRequest.getDestination() != null) {
            trains = trainRepository.findByDestinationIgnoreCase(searchRequest.getDestination());
        } else if (searchRequest.getStatus() != null) {
            trains = trainRepository.findByStatus(searchRequest.getStatus());
        } else {
            trains = trainRepository.findAll();
        }
        
        // Apply additional filters
        if (searchRequest.getDepartureTimeAfter() != null) {
            trains = trains.stream()
                    .filter(train -> train.getDepartureTime().isAfter(searchRequest.getDepartureTimeAfter()))
                    .collect(Collectors.toList());
        }
        
        if (searchRequest.getDepartureTimeBefore() != null) {
            trains = trains.stream()
                    .filter(train -> train.getDepartureTime().isBefore(searchRequest.getDepartureTimeBefore()))
                    .collect(Collectors.toList());
        }
        
        if (searchRequest.getTrainName() != null) {
            trains = trains.stream()
                    .filter(train -> train.getTrainName().toLowerCase().contains(searchRequest.getTrainName().toLowerCase()))
                    .collect(Collectors.toList());
        }
        
        return trains.stream()
                .map(TrainResponse::new)
                .collect(Collectors.toList());
    }

    @Override
    public TrainResponse updateTrain(Long trainId, TrainRequest request) {
        logger.info("Updating train with ID: {}", trainId);
        
        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> TrainNotFoundException.withId(trainId));
        
        // Check if new name conflicts with existing train (case-insensitive)
        if (!train.getTrainName().equalsIgnoreCase(request.getTrainName()) && 
            trainRepository.existsByTrainNameIgnoreCase(request.getTrainName())) {
            throw TrainAlreadyExistsException.withName(request.getTrainName());
        }
        
        // Validate arrival time is after departure time
        if (request.getArrivalTime().isBefore(request.getDepartureTime())) {
            throw new IllegalArgumentException("Arrival time must be after departure time");
        }
        
        // Update train fields
        train.setTrainName(request.getTrainName());
        train.setSource(request.getSource());
        train.setDestination(request.getDestination());
        train.setDepartureTime(request.getDepartureTime());
        train.setArrivalTime(request.getArrivalTime());
        train.setStatus(request.getStatus());
        
        Train updatedTrain = trainRepository.save(train);
        
        // Update schedules if provided
        if (request.getScheduleDays() != null) {
            updateSchedules(updatedTrain, request.getScheduleDays());
        }
        
        // Update fare types if provided
        if (request.getFareTypes() != null) {
            updateFareTypes(updatedTrain, request.getFareTypes());
        }
        
        logger.info("Train updated successfully with ID: {}", updatedTrain.getTrainId());
        return new TrainResponse(updatedTrain);
    }

    @Override
    public TrainResponse updateTrainStatus(Long trainId, TrainStatus status) {
        logger.info("Updating train status for ID: {} to {}", trainId, status);
        
        Train train = trainRepository.findById(trainId)
                .orElseThrow(() -> TrainNotFoundException.withId(trainId));
        
        train.setStatus(status);
        Train updatedTrain = trainRepository.save(train);
        
        logger.info("Train status updated successfully for ID: {}", trainId);
        return new TrainResponse(updatedTrain);
    }

    @Override
    public void deleteTrain(Long trainId) {
        logger.info("Deleting train with ID: {}", trainId);
        
        if (!trainRepository.existsById(trainId)) {
            throw TrainNotFoundException.withId(trainId);
        }
        
        trainRepository.deleteById(trainId);
        logger.info("Train deleted successfully with ID: {}", trainId);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByTrainName(String trainName) {
        return trainRepository.existsByTrainNameIgnoreCase(trainName);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByRoute(String source, String destination) {
        return trainRepository.existsBySourceIgnoreCaseAndDestinationIgnoreCase(source, destination);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainResponse> getAvailableTrainsForDate(String source, String destination, LocalDate journeyDate) {
        logger.debug("Fetching available trains for date: {} from {} to {}", journeyDate, source, destination);
        
        // Get day of week for the journey date
        java.time.DayOfWeek javaDayOfWeek = journeyDate.getDayOfWeek();
        String dayOfWeekStr = javaDayOfWeek.name().substring(0, 3).toUpperCase();
        
        try {
            DayOfWeek dayOfWeekEnum = DayOfWeek.valueOf(dayOfWeekStr);
            // Get trains that run on this day and have the specified route (case-insensitive)
            List<Train> trains = trainRepository.findTrainsByScheduleDayAndRoute(dayOfWeekEnum, source, destination);
            
            // Filter trains with available seats
            return trains.stream()
                    .filter(train -> train.getFareTypes().stream().anyMatch(fare -> fare.getSeatsAvailable() > 0))
                    .map(TrainResponse::new)
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid day of week conversion: {}", dayOfWeekStr);
            return new ArrayList<>();
        }
    }

    // Helper methods
    private void createSchedules(Train train, List<String> scheduleDays) {
        List<TrainSchedule> schedules = new ArrayList<>();
        
        for (String day : scheduleDays) {
            try {
                DayOfWeek dayOfWeek = DayOfWeek.valueOf(day.toUpperCase());
                TrainSchedule schedule = new TrainSchedule(train, dayOfWeek);
                schedules.add(schedule);
            } catch (IllegalArgumentException e) {
                logger.warn("Invalid day of week: {}", day);
            }
        }
        
        scheduleRepository.saveAll(schedules);
    }

    private void createFareTypes(Train train, List<com.tcs.trainTicketManagementSystem.train.dto.FareTypeRequest> fareTypeRequests) {
        List<FareType> fareTypes = new ArrayList<>();
        
        for (com.tcs.trainTicketManagementSystem.train.dto.FareTypeRequest request : fareTypeRequests) {
            FareType fareType = new FareType(train, request.getClassType(), request.getPrice(), request.getSeatsAvailable());
            fareTypes.add(fareType);
        }
        
        fareTypeRepository.saveAll(fareTypes);
    }

    private void updateSchedules(Train train, List<String> scheduleDays) {
        // Delete existing schedules
        scheduleRepository.deleteByTrainTrainId(train.getTrainId());
        
        // Create new schedules
        if (!scheduleDays.isEmpty()) {
            createSchedules(train, scheduleDays);
        }
    }

    private void updateFareTypes(Train train, List<com.tcs.trainTicketManagementSystem.train.dto.FareTypeRequest> fareTypeRequests) {
        // Delete existing fare types
        fareTypeRepository.deleteByTrainTrainId(train.getTrainId());
        
        // Create new fare types
        if (!fareTypeRequests.isEmpty()) {
            createFareTypes(train, fareTypeRequests);
        }
    }
} 