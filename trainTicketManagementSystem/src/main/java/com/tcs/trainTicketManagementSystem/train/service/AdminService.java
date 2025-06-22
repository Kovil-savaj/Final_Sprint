package com.tcs.trainTicketManagementSystem.train.service;

import com.tcs.trainTicketManagementSystem.train.dto.AdminDashboardResponse;

/**
 * Service interface for Admin operations.
 */
public interface AdminService {

    /**
     * Get admin dashboard statistics.
     * This method provides comprehensive statistics for the admin dashboard.
     * 
     * @return AdminDashboardResponse containing all dashboard statistics
     */
    AdminDashboardResponse getDashboardStatistics();
} 