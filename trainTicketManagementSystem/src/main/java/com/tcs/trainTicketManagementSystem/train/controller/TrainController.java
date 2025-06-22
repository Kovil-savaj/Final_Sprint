package com.tcs.trainTicketManagementSystem.train.controller;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tcs.trainTicketManagementSystem.train.dto.TrainRequest;
import com.tcs.trainTicketManagementSystem.train.dto.TrainResponse;
import com.tcs.trainTicketManagementSystem.train.dto.TrainSearchRequest;
import com.tcs.trainTicketManagementSystem.train.dto.StationListResponse;
import com.tcs.trainTicketManagementSystem.train.model.TrainStatus;
import com.tcs.trainTicketManagementSystem.train.service.TrainService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * REST Controller for Train operations.
 */
@RestController
@RequestMapping("/api/v1/trains")
@Validated
@Tag(name = "Train Management", description = "APIs for managing trains, schedules, and fare types")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class TrainController {

    private static final Logger logger = LoggerFactory.getLogger(TrainController.class);

    private final TrainService trainService;

    public TrainController(TrainService trainService) {
        this.trainService = trainService;
    }

    @PostMapping
    @Operation(summary = "Create a new train", description = "Creates a new train with schedules and fare types")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Train created successfully",
            content = @Content(schema = @Schema(implementation = TrainResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "409", description = "Train already exists")
    })
    public ResponseEntity<TrainResponse> createTrain(
            @Parameter(description = "Train creation details", required = true)
            @Valid @RequestBody TrainRequest request) {
        logger.info("Received train creation request for train: {}", request.getTrainName());
        TrainResponse response = trainService.createTrain(request);
        logger.info("Train created successfully with ID: {}", response.getTrainId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{trainId}")
    @Operation(summary = "Get train by ID", description = "Retrieves train information by train ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Train found",
            content = @Content(schema = @Schema(implementation = TrainResponse.class))),
        @ApiResponse(responseCode = "404", description = "Train not found")
    })
    public ResponseEntity<TrainResponse> getTrainById(
            @Parameter(description = "Train ID", required = true)
            @PathVariable Long trainId) {
        logger.debug("Fetching train by ID: {}", trainId);
        TrainResponse response = trainService.getTrainById(trainId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/name/{trainName}")
    @Operation(summary = "Get train by name", description = "Retrieves train information by train name")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Train found",
            content = @Content(schema = @Schema(implementation = TrainResponse.class))),
        @ApiResponse(responseCode = "404", description = "Train not found")
    })
    public ResponseEntity<TrainResponse> getTrainByName(
            @Parameter(description = "Train name", required = true)
            @PathVariable String trainName) {
        logger.debug("Fetching train by name: {}", trainName);
        TrainResponse response = trainService.getTrainByName(trainName);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all trains", description = "Retrieves all trains in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Trains retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainResponse.class)))
    })
    public ResponseEntity<List<TrainResponse>> getAllTrains() {
        logger.debug("Fetching all trains");
        List<TrainResponse> trains = trainService.getAllTrains();
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get trains by status", description = "Retrieves all trains with a specific status")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Trains retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid status")
    })
    public ResponseEntity<List<TrainResponse>> getTrainsByStatus(
            @Parameter(description = "Train status (ACTIVE or INACTIVE)", required = true)
            @PathVariable TrainStatus status) {
        logger.debug("Fetching trains by status: {}", status);
        List<TrainResponse> trains = trainService.getTrainsByStatus(status);
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/route")
    @Operation(summary = "Get trains by route", description = "Retrieves trains for a specific source and destination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Trains retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainResponse.class)))
    })
    public ResponseEntity<List<TrainResponse>> getTrainsByRoute(
            @Parameter(description = "Source station", required = true)
            @RequestParam String source,
            @Parameter(description = "Destination station", required = true)
            @RequestParam String destination) {
        logger.debug("Fetching trains for route: {} to {}", source, destination);
        List<TrainResponse> trains = trainService.getTrainsByRoute(source, destination);
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/source/{source}")
    @Operation(summary = "Get trains by source", description = "Retrieves all trains from a specific source station")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Trains retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainResponse.class)))
    })
    public ResponseEntity<List<TrainResponse>> getTrainsBySource(
            @Parameter(description = "Source station", required = true)
            @PathVariable String source) {
        logger.debug("Fetching trains by source: {}", source);
        List<TrainResponse> trains = trainService.getTrainsBySource(source);
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/destination/{destination}")
    @Operation(summary = "Get trains by destination", description = "Retrieves all trains to a specific destination station")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Trains retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainResponse.class)))
    })
    public ResponseEntity<List<TrainResponse>> getTrainsByDestination(
            @Parameter(description = "Destination station", required = true)
            @PathVariable String destination) {
        logger.debug("Fetching trains by destination: {}", destination);
        List<TrainResponse> trains = trainService.getTrainsByDestination(destination);
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/search/name")
    @Operation(summary = "Search trains by name", description = "Searches trains by name pattern")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search completed successfully",
            content = @Content(schema = @Schema(implementation = TrainResponse.class)))
    })
    public ResponseEntity<List<TrainResponse>> searchTrainsByName(
            @Parameter(description = "Train name pattern to search for", required = true)
            @RequestParam String trainName) {
        logger.debug("Searching trains by name pattern: {}", trainName);
        List<TrainResponse> trains = trainService.searchTrainsByName(trainName);
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/search/source")
    @Operation(summary = "Search trains by source", description = "Searches trains by source station pattern")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search completed successfully",
            content = @Content(schema = @Schema(implementation = TrainResponse.class)))
    })
    public ResponseEntity<List<TrainResponse>> searchTrainsBySource(
            @Parameter(description = "Source station pattern to search for", required = true)
            @RequestParam String source) {
        logger.debug("Searching trains by source pattern: {}", source);
        List<TrainResponse> trains = trainService.searchTrainsBySource(source);
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/search/destination")
    @Operation(summary = "Search trains by destination", description = "Searches trains by destination station pattern")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search completed successfully",
            content = @Content(schema = @Schema(implementation = TrainResponse.class)))
    })
    public ResponseEntity<List<TrainResponse>> searchTrainsByDestination(
            @Parameter(description = "Destination station pattern to search for", required = true)
            @RequestParam String destination) {
        logger.debug("Searching trains by destination pattern: {}", destination);
        List<TrainResponse> trains = trainService.searchTrainsByDestination(destination);
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/schedule/{dayOfWeek}")
    @Operation(summary = "Get trains by schedule day", description = "Retrieves trains that run on a specific day of the week")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Trains retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainResponse.class)))
    })
    public ResponseEntity<List<TrainResponse>> getTrainsByScheduleDay(
            @Parameter(description = "Day of week (MON, TUE, WED, THU, FRI, SAT, SUN)", required = true)
            @PathVariable String dayOfWeek) {
        logger.debug("Fetching trains by schedule day: {}", dayOfWeek);
        List<TrainResponse> trains = trainService.getTrainsByScheduleDay(dayOfWeek);
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/schedule/{dayOfWeek}/route")
    @Operation(summary = "Get trains by schedule day and route", description = "Retrieves trains that run on a specific day for a specific route")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Trains retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainResponse.class)))
    })
    public ResponseEntity<List<TrainResponse>> getTrainsByScheduleDayAndRoute(
            @Parameter(description = "Day of week (MON, TUE, WED, THU, FRI, SAT, SUN)", required = true)
            @PathVariable String dayOfWeek,
            @Parameter(description = "Source station", required = true)
            @RequestParam String source,
            @Parameter(description = "Destination station", required = true)
            @RequestParam String destination) {
        logger.debug("Fetching trains by schedule day and route: {} for {} to {}", dayOfWeek, source, destination);
        List<TrainResponse> trains = trainService.getTrainsByScheduleDayAndRoute(dayOfWeek, source, destination);
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/available-seats")
    @Operation(summary = "Get trains with available seats", description = "Retrieves all trains that have available seats")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Trains retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainResponse.class)))
    })
    public ResponseEntity<List<TrainResponse>> getTrainsWithAvailableSeats() {
        logger.debug("Fetching trains with available seats");
        List<TrainResponse> trains = trainService.getTrainsWithAvailableSeats();
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/available-seats/route")
    @Operation(summary = "Get trains with available seats for route", description = "Retrieves trains with available seats for a specific route")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Trains retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainResponse.class)))
    })
    public ResponseEntity<List<TrainResponse>> getTrainsWithAvailableSeatsForRoute(
            @Parameter(description = "Source station", required = true)
            @RequestParam String source,
            @Parameter(description = "Destination station", required = true)
            @RequestParam String destination) {
        logger.debug("Fetching trains with available seats for route: {} to {}", source, destination);
        List<TrainResponse> trains = trainService.getTrainsWithAvailableSeatsForRoute(source, destination);
        return ResponseEntity.ok(trains);
    }

    @PostMapping("/search")
    @Operation(summary = "Search trains with criteria", description = "Searches trains with various criteria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Search completed successfully",
            content = @Content(schema = @Schema(implementation = TrainResponse.class)))
    })
    public ResponseEntity<List<TrainResponse>> searchTrains(
            @Parameter(description = "Search criteria", required = true)
            @Valid @RequestBody TrainSearchRequest searchRequest) {
        logger.debug("Searching trains with criteria: {}", searchRequest);
        List<TrainResponse> trains = trainService.searchTrains(searchRequest);
        return ResponseEntity.ok(trains);
    }

    @GetMapping("/available")
    @Operation(summary = "Get available trains for date", description = "Retrieves available trains for a specific journey date")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Trains retrieved successfully",
            content = @Content(schema = @Schema(implementation = TrainResponse.class)))
    })
    public ResponseEntity<List<TrainResponse>> getAvailableTrainsForDate(
            @Parameter(description = "Source station", required = true)
            @RequestParam String source,
            @Parameter(description = "Destination station", required = true)
            @RequestParam String destination,
            @Parameter(description = "Journey date", required = true)
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate journeyDate) {
        logger.debug("Fetching available trains for date: {} from {} to {}", journeyDate, source, destination);
        List<TrainResponse> trains = trainService.getAvailableTrainsForDate(source, destination, journeyDate);
        return ResponseEntity.ok(trains);
    }

    @PutMapping("/{trainId}")
    @Operation(summary = "Update train", description = "Updates train information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Train updated successfully",
            content = @Content(schema = @Schema(implementation = TrainResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "Train not found"),
        @ApiResponse(responseCode = "409", description = "Train name already exists")
    })
    public ResponseEntity<TrainResponse> updateTrain(
            @Parameter(description = "Train ID", required = true)
            @PathVariable Long trainId,
            @Parameter(description = "Updated train information", required = true)
            @Valid @RequestBody TrainRequest request) {
        logger.info("Updating train with ID: {}", trainId);
        TrainResponse response = trainService.updateTrain(trainId, request);
        logger.info("Train updated successfully with ID: {}", response.getTrainId());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{trainId}/status")
    @Operation(summary = "Update train status", description = "Updates the status of a train")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Train status updated successfully",
            content = @Content(schema = @Schema(implementation = TrainResponse.class))),
        @ApiResponse(responseCode = "404", description = "Train not found")
    })
    public ResponseEntity<TrainResponse> updateTrainStatus(
            @Parameter(description = "Train ID", required = true)
            @PathVariable Long trainId,
            @Parameter(description = "New train status", required = true)
            @RequestParam TrainStatus status) {
        logger.info("Updating train status for ID: {} to {}", trainId, status);
        TrainResponse response = trainService.updateTrainStatus(trainId, status);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{trainId}")
    @Operation(summary = "Delete train", description = "Deletes a train by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Train deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Train not found")
    })
    public ResponseEntity<Void> deleteTrain(
            @Parameter(description = "Train ID", required = true)
            @PathVariable Long trainId) {
        logger.info("Deleting train with ID: {}", trainId);
        trainService.deleteTrain(trainId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/name/{trainName}")
    @Operation(summary = "Check if train exists by name", description = "Checks if a train with the given name already exists")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Check completed successfully")
    })
    public ResponseEntity<Boolean> existsByTrainName(
            @Parameter(description = "Train name to check", required = true)
            @PathVariable String trainName) {
        logger.debug("Checking if train exists by name: {}", trainName);
        boolean exists = trainService.existsByTrainName(trainName);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/exists/route")
    @Operation(summary = "Check if train exists by route", description = "Checks if a train exists for the given source and destination")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Check completed successfully")
    })
    public ResponseEntity<Boolean> existsByRoute(
            @Parameter(description = "Source station", required = true)
            @RequestParam String source,
            @Parameter(description = "Destination station", required = true)
            @RequestParam String destination) {
        logger.debug("Checking if train exists for route: {} to {}", source, destination);
        boolean exists = trainService.existsByRoute(source, destination);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/stations")
    @Operation(summary = "Get all distinct stations", description = "Retrieves all distinct source and destination stations available in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Stations retrieved successfully",
            content = @Content(schema = @Schema(implementation = StationListResponse.class)))
    })
    public ResponseEntity<StationListResponse> getDistinctStations() {
        logger.debug("Fetching distinct source and destination stations");
        StationListResponse response = trainService.getDistinctStations();
        return ResponseEntity.ok(response);
    }
} 