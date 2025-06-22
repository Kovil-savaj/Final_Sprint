package com.tcs.trainTicketManagementSystem.train.service;

import com.tcs.trainTicketManagementSystem.train.dto.AdminDashboardResponse;
import com.tcs.trainTicketManagementSystem.train.model.TrainStatus;
import com.tcs.trainTicketManagementSystem.train.repository.TrainRepository;
import com.tcs.trainTicketManagementSystem.booking.repository.BookingRepository;
import com.tcs.trainTicketManagementSystem.booking.model.BookingStatus;
import com.tcs.trainTicketManagementSystem.users.repository.UserRepository;
import com.tcs.trainTicketManagementSystem.users.model.UserRole;
import com.tcs.trainTicketManagementSystem.booking.repository.PassengerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * Service implementation for Admin operations.
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final TrainRepository trainRepository;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final PassengerRepository passengerRepository;

    public AdminServiceImpl(TrainRepository trainRepository, 
                           BookingRepository bookingRepository,
                           UserRepository userRepository,
                           PassengerRepository passengerRepository) {
        this.trainRepository = trainRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.passengerRepository = passengerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public AdminDashboardResponse getDashboardStatistics() {
        logger.info("Fetching admin dashboard statistics");
        
        try {
            // Train statistics
            Long totalTrains = trainRepository.count();
            Long activeTrains = trainRepository.countByStatus(TrainStatus.ACTIVE);
            Long inactiveTrains = trainRepository.countByStatus(TrainStatus.INACTIVE);
            
            // Booking statistics
            Long totalBookings = bookingRepository.count();
            Long confirmedBookings = bookingRepository.countByStatus(BookingStatus.CONFIRMED);
            Long cancelledBookings = bookingRepository.countByStatus(BookingStatus.CANCELLED);
            
            // Sales calculation
            BigDecimal totalSales = bookingRepository.sumTotalFareByStatus(BookingStatus.CONFIRMED);
            if (totalSales == null) {
                totalSales = BigDecimal.ZERO;
            }
            
            // User and passenger statistics
            Long totalUsers = userRepository.countByRole(UserRole.USER);
            Long totalPassengers = passengerRepository.count();
            
            logger.info("Dashboard statistics calculated successfully: {} trains, {} bookings, {} users, {} passengers", 
                       totalTrains, totalBookings, totalUsers, totalPassengers);
            
            return new AdminDashboardResponse(
                totalBookings,
                totalSales,
                confirmedBookings,
                cancelledBookings,
                activeTrains,
                inactiveTrains,
                totalTrains,
                totalUsers,
                totalPassengers
            );
            
        } catch (Exception e) {
            logger.error("Error calculating dashboard statistics", e);
            throw new RuntimeException("Failed to calculate dashboard statistics", e);
        }
    }
} 