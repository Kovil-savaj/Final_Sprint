package com.tcs.trainTicketManagementSystem.train.controller;

import com.tcs.trainTicketManagementSystem.train.dto.AdminDashboardResponse;
import com.tcs.trainTicketManagementSystem.train.service.AdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST Controller for Admin operations.
 * This controller provides admin-only endpoints for dashboard and administrative functions.
 */
@RestController
@RequestMapping("/api/v1/admin")
@Tag(name = "Admin Management", description = "APIs for admin dashboard and administrative functions")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/dashboard")
    @Operation(summary = "Get admin dashboard statistics", 
               description = "Retrieves comprehensive statistics for the admin dashboard including bookings, sales, trains, users, and passengers")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Dashboard statistics retrieved successfully",
            content = @Content(schema = @Schema(implementation = AdminDashboardResponse.class))),
        @ApiResponse(responseCode = "403", description = "Access denied - Admin privileges required"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<AdminDashboardResponse> getDashboardStatistics() {
        logger.info("Admin dashboard statistics requested");
        
        try {
            AdminDashboardResponse response = adminService.getDashboardStatistics();
            logger.info("Dashboard statistics retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error retrieving dashboard statistics", e);
            throw e;
        }
    }
} 