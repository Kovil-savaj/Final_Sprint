package com.tcs.trainTicketManagementSystem.booking.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcs.trainTicketManagementSystem.booking.dto.BookingRequest;
import com.tcs.trainTicketManagementSystem.booking.dto.BookingResponse;
import com.tcs.trainTicketManagementSystem.booking.dto.BookingSearchRequest;
import com.tcs.trainTicketManagementSystem.booking.dto.PassengerRequest;
import com.tcs.trainTicketManagementSystem.booking.dto.PassengerResponse;
import com.tcs.trainTicketManagementSystem.booking.exception.BookingNotFoundException;
import com.tcs.trainTicketManagementSystem.booking.exception.BookingValidationException;
import com.tcs.trainTicketManagementSystem.booking.exception.PassengerNotFoundException;
import com.tcs.trainTicketManagementSystem.booking.model.Booking;
import com.tcs.trainTicketManagementSystem.booking.model.BookingStatus;
import com.tcs.trainTicketManagementSystem.booking.model.Passenger;
import com.tcs.trainTicketManagementSystem.booking.repository.BookingRepository;
import com.tcs.trainTicketManagementSystem.booking.repository.PassengerRepository;
import com.tcs.trainTicketManagementSystem.train.model.FareType;
import com.tcs.trainTicketManagementSystem.train.model.Train;
import com.tcs.trainTicketManagementSystem.train.repository.FareTypeRepository;
import com.tcs.trainTicketManagementSystem.train.repository.TrainRepository;
import com.tcs.trainTicketManagementSystem.users.model.User;
import com.tcs.trainTicketManagementSystem.users.repository.UserRepository;

/**
 * Service implementation for Booking and Passenger operations.
 */
@Service
@Transactional
public class BookingServiceImpl implements BookingService {

    private static final Logger logger = LoggerFactory.getLogger(BookingServiceImpl.class);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PassengerRepository passengerRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private FareTypeRepository fareTypeRepository;

    // Basic Booking Operations
    @Override
    public BookingResponse createBooking(BookingRequest request) {
        logger.info("Creating booking for user: {}, train: {}", request.getUserId(), request.getTrainId());

        // Validate request
        validateBookingRequest(request);

        // Get related entities
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BookingValidationException("User with ID " + request.getUserId() + " not found"));

        Train train = trainRepository.findById(request.getTrainId())
                .orElseThrow(() -> new BookingValidationException("Train with ID " + request.getTrainId() + " not found"));

        FareType fareType = fareTypeRepository.findById(request.getFareTypeId())
                .orElseThrow(() -> new BookingValidationException("Fare type with ID " + request.getFareTypeId() + " not found"));

        // Validate fare type belongs to the train
        if (!fareType.getTrain().getTrainId().equals(train.getTrainId())) {
            throw new BookingValidationException("Fare type does not belong to the specified train");
        }

        // Check seat availability
        if (fareType.getSeatsAvailable() < request.getPassengers().size()) {
            throw new BookingValidationException("Not enough seats available. Available: "
                    + fareType.getSeatsAvailable() + ", Required: " + request.getPassengers().size());
        }

        // Create booking
        final Booking booking = new Booking(user, train, fareType, request.getJourneyDate(), request.getTotalFare());
        final Booking savedBooking = bookingRepository.save(booking);

        // Create passengers
        List<Passenger> passengers = request.getPassengers().stream()
                .map(passengerRequest -> new Passenger(savedBooking, passengerRequest.getName(),
                passengerRequest.getAge(), passengerRequest.getGender(),
                passengerRequest.getIdProof()))
                .collect(Collectors.toList());

        passengers = passengerRepository.saveAll(passengers);
        savedBooking.setPassengers(passengers);

        // Update seat availability
        fareType.setSeatsAvailable(fareType.getSeatsAvailable() - passengers.size());
        fareTypeRepository.save(fareType);

        logger.info("Booking created successfully with ID: {}", savedBooking.getBookingId());
        return convertToBookingResponse(savedBooking);
    }

    @Override
    public BookingResponse getBookingById(Long bookingId) {
        logger.info("Getting booking by ID: {}", bookingId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking with ID " + bookingId + " not found"));

        return convertToBookingResponse(booking);
    }

    @Override
    public List<BookingResponse> getAllBookings() {
        logger.info("Getting all bookings");

        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(this::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getBookingsByUserId(Long userId) {
        logger.info("Getting bookings for user ID: {}", userId);

        List<Booking> bookings = bookingRepository.findByUserIdOrderByBookingDateDesc(userId);
        return bookings.stream()
                .map(this::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getBookingsByUserIdAndStatus(Long userId, BookingStatus status) {
        logger.info("Getting bookings for user ID: {} with status: {}", userId, status);

        List<Booking> bookings = bookingRepository.findByUserIdAndStatusOrderByBookingDateDesc(userId, status);
        return bookings.stream()
                .map(this::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getUpcomingBookingsByUserId(Long userId) {
        logger.info("Getting upcoming bookings for user ID: {}", userId);

        List<Booking> bookings = bookingRepository.findUpcomingBookingsByUserId(userId, LocalDate.now());
        return bookings.stream()
                .map(this::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getPastBookingsByUserId(Long userId) {
        logger.info("Getting past bookings for user ID: {}", userId);

        List<Booking> bookings = bookingRepository.findPastBookingsByUserId(userId, LocalDate.now());
        return bookings.stream()
                .map(this::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponse cancelBooking(Long bookingId) {
        logger.info("Cancelling booking with ID: {}", bookingId);

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking with ID " + bookingId + " not found"));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BookingValidationException("Booking is already cancelled");
        }

        // Update seat availability
        FareType fareType = booking.getFareType();
        fareType.setSeatsAvailable(fareType.getSeatsAvailable() + booking.getPassengers().size());
        fareTypeRepository.save(fareType);

        booking.setStatus(BookingStatus.CANCELLED);
        booking = bookingRepository.save(booking);

        return convertToBookingResponse(booking);
    }

    @Override
    public boolean existsByBookingId(Long bookingId) {
        return bookingRepository.existsByBookingId(bookingId);
    }

    // Basic Passenger Operations
    @Override
    public List<PassengerResponse> getPassengersByBookingId(Long bookingId) {
        logger.info("Getting passengers for booking ID: {}", bookingId);

        List<Passenger> passengers = passengerRepository.findByBooking_BookingIdOrderByPassengerId(bookingId);
        return passengers.stream()
                .map(this::convertToPassengerResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PassengerResponse getPassengerById(Long passengerId) {
        logger.info("Getting passenger by ID: {}", passengerId);

        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger with ID " + passengerId + " not found"));

        return convertToPassengerResponse(passenger);
    }

    @Override
    public PassengerResponse getPassengerByIdProof(String idProof) {
        logger.info("Getting passenger by ID proof: {}", idProof);

        Passenger passenger = passengerRepository.findByIdProof(idProof)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger with ID proof " + idProof + " not found"));

        return convertToPassengerResponse(passenger);
    }

    // Helper Methods
    private void validateBookingRequest(BookingRequest request) {
        if (request.getJourneyDate().isBefore(LocalDate.now())) {
            throw new BookingValidationException("Journey date cannot be in the past");
        }

        if (request.getPassengers().size() > 10) {
            throw new BookingValidationException("Maximum 10 passengers allowed per booking");
        }
    }

    private BookingResponse convertToBookingResponse(Booking booking) {
        List<PassengerResponse> passengerResponses = booking.getPassengers() != null
                ? booking.getPassengers().stream()
                        .map(this::convertToPassengerResponse)
                        .collect(Collectors.toList()) : null;

        return new BookingResponse(
                booking.getBookingId(),
                booking.getUser().getUserId(),
                booking.getUser().getUsername(),
                booking.getTrain().getTrainId(),
                booking.getTrain().getTrainName(),
                booking.getTrain().getSource(),
                booking.getTrain().getDestination(),
                booking.getTrain().getDepartureTime().toString(),
                booking.getTrain().getComputedArrivalTime().toString(),
                booking.getFareType().getFareTypeId(),
                booking.getFareType().getClassType().toString(),
                booking.getFareType().getPrice(),
                booking.getJourneyDate(),
                booking.getBookingDate(),
                booking.getTotalFare(),
                booking.getStatus().toString(),
                passengerResponses
        );
    }

    private PassengerResponse convertToPassengerResponse(Passenger passenger) {
        return new PassengerResponse(
                passenger.getPassengerId(),
                passenger.getBooking().getBookingId(),
                passenger.getName(),
                passenger.getAge(),
                passenger.getGender(),
                passenger.getIdProof()
        );
    }

    // Stub implementations for remaining methods
    // These can be implemented as needed
    @Override
    public List<BookingResponse> getBookingsByTrainId(Long trainId) {
        List<Booking> bookings = bookingRepository.findByTrainIdOrderByBookingDateDesc(trainId);
        return bookings.stream().map(this::convertToBookingResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getBookingsByFareTypeId(Long fareTypeId) {
        List<Booking> bookings = bookingRepository.findByFareTypeIdOrderByBookingDateDesc(fareTypeId);
        return bookings.stream().map(this::convertToBookingResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getBookingsByStatus(BookingStatus status) {
        List<Booking> bookings = bookingRepository.findByStatusOrderByBookingDateDesc(status);
        return bookings.stream().map(this::convertToBookingResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getBookingsByJourneyDate(LocalDate journeyDate) {
        List<Booking> bookings = bookingRepository.findByJourneyDateOrderByBookingDateDesc(journeyDate);
        return bookings.stream().map(this::convertToBookingResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getBookingsByJourneyDateRange(LocalDate fromDate, LocalDate toDate) {
        List<Booking> bookings = bookingRepository.findByJourneyDateBetweenOrderByJourneyDateAsc(fromDate, toDate);
        return bookings.stream().map(this::convertToBookingResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getBookingsByBookingDateRange(LocalDate fromDate, LocalDate toDate) {
        List<Booking> bookings = bookingRepository.findByBookingDateBetweenOrderByBookingDateDesc(fromDate, toDate);
        return bookings.stream().map(this::convertToBookingResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> getBookingsByRoute(String source, String destination) {
        List<Booking> bookings = bookingRepository.findBySourceAndDestination(source, destination);
        return bookings.stream().map(this::convertToBookingResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> searchBookingsByTrainName(String trainName) {
        List<Booking> bookings = bookingRepository.findByTrainNameContaining(trainName);
        return bookings.stream().map(this::convertToBookingResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> searchBookingsByUsername(String username) {
        List<Booking> bookings = bookingRepository.findByUsernameContaining(username);
        return bookings.stream().map(this::convertToBookingResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> searchBookingsBySource(String source) {
        List<Booking> bookings = bookingRepository.findBySourceContaining(source);
        return bookings.stream().map(this::convertToBookingResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> searchBookingsByDestination(String destination) {
        List<Booking> bookings = bookingRepository.findByDestinationContaining(destination);
        return bookings.stream().map(this::convertToBookingResponse).collect(Collectors.toList());
    }

    @Override
    public List<BookingResponse> searchBookings(BookingSearchRequest searchRequest) {
        // Simplified implementation
        List<Booking> allBookings = bookingRepository.findAll();
        return allBookings.stream()
                .filter(booking -> matchesSearchCriteria(booking, searchRequest))
                .map(this::convertToBookingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public BookingResponse updateBookingStatus(Long bookingId, BookingStatus status) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking with ID " + bookingId + " not found"));
        booking.setStatus(status);
        booking = bookingRepository.save(booking);
        return convertToBookingResponse(booking);
    }

    @Override
    public void deleteBooking(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking with ID " + bookingId + " not found"));
        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BookingValidationException("Cannot delete a cancelled booking");
        }
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public boolean existsByUserIdAndTrainIdAndJourneyDate(Long userId, Long trainId, LocalDate journeyDate) {
        return bookingRepository.existsByUserIdAndTrainIdAndJourneyDate(userId, trainId, journeyDate);
    }

    @Override
    public BookingStatistics getBookingStatisticsByUserId(Long userId) {
        long totalBookings = bookingRepository.countByUserId(userId);
        long confirmedBookings = bookingRepository.countByUserIdAndStatus(userId, BookingStatus.CONFIRMED);
        long cancelledBookings = bookingRepository.countByUserIdAndStatus(userId, BookingStatus.CANCELLED);
        List<Booking> upcomingBookings = bookingRepository.findUpcomingBookingsByUserId(userId, LocalDate.now());
        List<Booking> pastBookings = bookingRepository.findPastBookingsByUserId(userId, LocalDate.now());
        return new BookingStatistics(totalBookings, confirmedBookings, cancelledBookings, upcomingBookings.size(), pastBookings.size());
    }

    @Override
    public List<PassengerResponse> searchPassengersByName(String name) {
        List<Passenger> passengers = passengerRepository.findByNameContainingIgnoreCaseOrderByName(name);
        return passengers.stream().map(this::convertToPassengerResponse).collect(Collectors.toList());
    }

    @Override
    public List<PassengerResponse> getPassengersByAgeRange(Integer minAge, Integer maxAge) {
        List<Passenger> passengers = passengerRepository.findByAgeBetweenOrderByAge(minAge, maxAge);
        return passengers.stream().map(this::convertToPassengerResponse).collect(Collectors.toList());
    }

    @Override
    public List<PassengerResponse> getPassengersByGender(String gender) {
        List<Passenger> passengers = passengerRepository.findByGenderOrderByName(gender);
        return passengers.stream().map(this::convertToPassengerResponse).collect(Collectors.toList());
    }

    @Override
    public List<PassengerResponse> searchPassengersByIdProof(String idProof) {
        List<Passenger> passengers = passengerRepository.findByIdProofContaining(idProof);
        return passengers.stream().map(this::convertToPassengerResponse).collect(Collectors.toList());
    }

    @Override
    public PassengerResponse addPassengerToBooking(Long bookingId, PassengerRequest passengerRequest) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFoundException("Booking with ID " + bookingId + " not found"));

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BookingValidationException("Cannot add passenger to cancelled booking");
        }

        if (booking.getFareType().getSeatsAvailable() < 1) {
            throw new BookingValidationException("No seats available for this booking");
        }

        if (passengerRepository.existsByBooking_BookingIdAndIdProof(bookingId, passengerRequest.getIdProof())) {
            throw new BookingValidationException("Passenger with ID proof " + passengerRequest.getIdProof() + " already exists in this booking");
        }

        Passenger passenger = new Passenger(booking, passengerRequest.getName(),
                passengerRequest.getAge(), passengerRequest.getGender(),
                passengerRequest.getIdProof());
        passenger = passengerRepository.save(passenger);

        FareType fareType = booking.getFareType();
        fareType.setSeatsAvailable(fareType.getSeatsAvailable() - 1);
        fareTypeRepository.save(fareType);

        return convertToPassengerResponse(passenger);
    }

    @Override
    public PassengerResponse updatePassenger(Long passengerId, PassengerRequest passengerRequest) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger with ID " + passengerId + " not found"));

        if (passenger.getBooking().getStatus() == BookingStatus.CANCELLED) {
            throw new BookingValidationException("Cannot update passenger in cancelled booking");
        }

        if (!passenger.getIdProof().equals(passengerRequest.getIdProof())
                && passengerRepository.existsByBooking_BookingIdAndIdProof(passenger.getBooking().getBookingId(), passengerRequest.getIdProof())) {
            throw new BookingValidationException("Passenger with ID proof " + passengerRequest.getIdProof() + " already exists in this booking");
        }

        passenger.setName(passengerRequest.getName());
        passenger.setAge(passengerRequest.getAge());
        passenger.setGender(passengerRequest.getGender());
        passenger.setIdProof(passengerRequest.getIdProof());
        passenger = passengerRepository.save(passenger);

        return convertToPassengerResponse(passenger);
    }

    @Override
    public void deletePassenger(Long passengerId) {
        Passenger passenger = passengerRepository.findById(passengerId)
                .orElseThrow(() -> new PassengerNotFoundException("Passenger with ID " + passengerId + " not found"));

        if (passenger.getBooking().getStatus() == BookingStatus.CANCELLED) {
            throw new BookingValidationException("Cannot delete passenger from cancelled booking");
        }

        passengerRepository.deleteById(passengerId);

        FareType fareType = passenger.getBooking().getFareType();
        fareType.setSeatsAvailable(fareType.getSeatsAvailable() + 1);
        fareTypeRepository.save(fareType);
    }

    @Override
    public boolean existsPassengerByIdProof(String idProof) {
        return passengerRepository.existsByIdProof(idProof);
    }

    @Override
    public boolean existsPassengerByBookingIdAndIdProof(Long bookingId, String idProof) {
        return passengerRepository.existsByBooking_BookingIdAndIdProof(bookingId, idProof);
    }

    @Override
    public PassengerStatistics getPassengerStatistics() {
        List<Passenger> allPassengers = passengerRepository.findAll();
        long totalPassengers = allPassengers.size();
        long malePassengers = passengerRepository.countByGender("MALE");
        long femalePassengers = passengerRepository.countByGender("FEMALE");
        long otherPassengers = passengerRepository.countByGender("OTHER");
        double averageAge = allPassengers.stream().mapToInt(Passenger::getAge).average().orElse(0.0);
        return new PassengerStatistics(totalPassengers, malePassengers, femalePassengers, otherPassengers, averageAge);
    }

    private boolean matchesSearchCriteria(Booking booking, BookingSearchRequest searchRequest) {
        return (searchRequest.getUserId() == null || booking.getUser().getUserId().equals(searchRequest.getUserId())) &&
               (searchRequest.getTrainId() == null || booking.getTrain().getTrainId().equals(searchRequest.getTrainId())) &&
               (searchRequest.getFareTypeId() == null || booking.getFareType().getFareTypeId().equals(searchRequest.getFareTypeId())) &&
               (searchRequest.getJourneyDateFrom() == null || !booking.getJourneyDate().isBefore(searchRequest.getJourneyDateFrom())) &&
               (searchRequest.getJourneyDateTo() == null || !booking.getJourneyDate().isAfter(searchRequest.getJourneyDateTo())) &&
               (searchRequest.getStatus() == null || booking.getStatus().toString().equals(searchRequest.getStatus()));
    }
}
