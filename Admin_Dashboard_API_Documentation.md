# Admin Dashboard API Documentation

## Overview
This document describes the Admin Dashboard API that provides comprehensive statistics for the Train Ticket Management System. This API is designed for administrative users to monitor system performance and key metrics.

## API Endpoint

### Get Admin Dashboard Statistics
**Endpoint:** `GET /api/v1/admin/dashboard`

**Description:** Retrieves comprehensive statistics for the admin dashboard including bookings, sales, trains, users, and passengers. This endpoint provides real-time data for administrative monitoring.

**URL:** `http://localhost:8081/api/v1/admin/dashboard`

**Method:** GET

**Headers:**
- `Content-Type: application/json`
- `Accept: application/json`

**Response Format:**
```json
{
    "totalBookings": 14,
    "totalSales": 47154.0,
    "confirmedBookings": 14,
    "cancelledBookings": 0,
    "activeTrains": 16,
    "inactiveTrains": 5,
    "totalTrains": 21,
    "totalUsers": 2,
    "totalPassengers": 22
}
```

**Response Fields:**
- `totalBookings`: Total number of bookings in the system
- `totalSales`: Total revenue from confirmed bookings (BigDecimal)
- `confirmedBookings`: Number of bookings with CONFIRMED status
- `cancelledBookings`: Number of bookings with CANCELLED status
- `activeTrains`: Number of trains with ACTIVE status
- `inactiveTrains`: Number of trains with INACTIVE status
- `totalTrains`: Total number of trains in the system
- `totalUsers`: Total number of registered users
- `totalPassengers`: Total number of passengers across all bookings

**HTTP Status Codes:**
- `200 OK`: Successfully retrieved dashboard statistics
- `403 Forbidden`: Access denied - Admin privileges required
- `500 Internal Server Error`: Server error occurred

## Implementation Details

### Files Created:

1. **AdminDashboardResponse.java** (New DTO)
   - Location: `src/main/java/com/tcs/trainTicketManagementSystem/train/dto/AdminDashboardResponse.java`
   - Purpose: Response DTO for admin dashboard statistics

2. **AdminService.java** (New Service Interface)
   - Location: `src/main/java/com/tcs/trainTicketManagementSystem/train/service/AdminService.java`
   - Purpose: Service interface for admin operations

3. **AdminServiceImpl.java** (New Service Implementation)
   - Location: `src/main/java/com/tcs/trainTicketManagementSystem/train/service/AdminServiceImpl.java`
   - Purpose: Implementation of admin service methods

4. **AdminController.java** (New Controller)
   - Location: `src/main/java/com/tcs/trainTicketManagementSystem/train/controller/AdminController.java`
   - Purpose: REST controller for admin endpoints

### Files Modified:

1. **TrainRepository.java** (Modified)
   - Added method: `countByStatus(TrainStatus status)`: Counts trains by status

2. **BookingRepository.java** (Modified)
   - Added method: `countByStatus(BookingStatus status)`: Counts bookings by status
   - Added method: `sumTotalFareByStatus(BookingStatus status)`: Sums total fare by status

### Database Queries Used:
```sql
-- Count trains by status
SELECT COUNT(*) FROM train WHERE status = ?

-- Count bookings by status  
SELECT COUNT(*) FROM booking WHERE status = ?

-- Sum total fare for confirmed bookings
SELECT SUM(total_fare) FROM booking WHERE status = 'CONFIRMED'

-- Count total users
SELECT COUNT(*) FROM users

-- Count total passengers
SELECT COUNT(*) FROM passenger
```

## Usage Examples

### cURL Example:
```bash
curl -X GET "http://localhost:8081/api/v1/admin/dashboard" \
     -H "accept: application/json"
```

### JavaScript/Frontend Example:
```javascript
fetch('/api/v1/admin/dashboard')
  .then(response => {
    if (!response.ok) {
      throw new Error('Admin access required');
    }
    return response.json();
  })
  .then(data => {
    console.log('Total Bookings:', data.totalBookings);
    console.log('Total Sales:', data.totalSales);
    console.log('Active Trains:', data.activeTrains);
    console.log('Total Users:', data.totalUsers);
    
    // Update dashboard UI
    updateDashboardUI(data);
  })
  .catch(error => console.error('Error:', error));
```

### React Example:
```jsx
import { useState, useEffect } from 'react';

function AdminDashboard() {
  const [stats, setStats] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  
  useEffect(() => {
    fetch('/api/v1/admin/dashboard')
      .then(response => {
        if (!response.ok) {
          throw new Error('Admin access required');
        }
        return response.json();
      })
      .then(data => setStats(data))
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  }, []);
  
  if (loading) return <div>Loading dashboard...</div>;
  if (error) return <div>Error: {error}</div>;
  if (!stats) return <div>No data available</div>;
  
  return (
    <div className="admin-dashboard">
      <h1>Admin Dashboard</h1>
      
      <div className="stats-grid">
        <div className="stat-card">
          <h3>Total Bookings</h3>
          <p>{stats.totalBookings}</p>
        </div>
        
        <div className="stat-card">
          <h3>Total Sales</h3>
          <p>₹{stats.totalSales.toLocaleString()}</p>
        </div>
        
        <div className="stat-card">
          <h3>Active Trains</h3>
          <p>{stats.activeTrains}</p>
        </div>
        
        <div className="stat-card">
          <h3>Total Users</h3>
          <p>{stats.totalUsers}</p>
        </div>
        
        <div className="stat-card">
          <h3>Confirmed Bookings</h3>
          <p>{stats.confirmedBookings}</p>
        </div>
        
        <div className="stat-card">
          <h3>Cancelled Bookings</h3>
          <p>{stats.cancelledBookings}</p>
        </div>
        
        <div className="stat-card">
          <h3>Total Trains</h3>
          <p>{stats.totalTrains}</p>
        </div>
        
        <div className="stat-card">
          <h3>Total Passengers</h3>
          <p>{stats.totalPassengers}</p>
        </div>
      </div>
    </div>
  );
}
```

## Security Considerations

### Admin-Only Access
This API endpoint is designed for administrative users only. In a production environment, you should implement:

1. **Authentication**: Verify user is logged in
2. **Authorization**: Verify user has ADMIN role
3. **Rate Limiting**: Prevent abuse
4. **Audit Logging**: Track admin actions

### Example Security Implementation:
```java
@PreAuthorize("hasRole('ADMIN')")
@GetMapping("/dashboard")
public ResponseEntity<AdminDashboardResponse> getDashboardStatistics() {
    // Implementation
}
```

## Performance Considerations

### Database Optimization
- All queries use indexed fields for optimal performance
- Read-only transactions for better concurrency
- Efficient aggregation queries

### Caching Strategy
For high-traffic scenarios, consider implementing:
- Redis caching for dashboard statistics
- Cache invalidation on data changes
- Scheduled cache refresh

## Error Handling

### Common Error Scenarios:
1. **Database Connection Issues**: Graceful degradation with error messages
2. **Permission Denied**: Clear 403 response
3. **Data Inconsistency**: Null handling for missing data

### Error Response Format:
```json
{
    "timestamp": "2025-06-23T00:12:32+05:30",
    "status": 500,
    "error": "Internal Server Error",
    "message": "Failed to calculate dashboard statistics",
    "path": "/api/v1/admin/dashboard"
}
```

## Testing

### Current Test Results:
Based on the sample data, the API returns:
- **Total Bookings**: 14
- **Total Sales**: ₹47,154
- **Confirmed Bookings**: 14
- **Cancelled Bookings**: 0
- **Active Trains**: 16
- **Inactive Trains**: 5
- **Total Trains**: 21
- **Total Users**: 2
- **Total Passengers**: 22

### Test Commands:
```bash
# Test the endpoint
curl -X GET "http://localhost:8081/api/v1/admin/dashboard" \
     -H "accept: application/json" | python3 -m json.tool

# Test with different content types
curl -X GET "http://localhost:8081/api/v1/admin/dashboard" \
     -H "Accept: application/xml"

# Test error handling
curl -X POST "http://localhost:8081/api/v1/admin/dashboard" \
     -H "Content-Type: application/json"
```

## Swagger Documentation

The endpoint is documented in Swagger UI and can be accessed at:
`http://localhost:8081/swagger-ui/index.html`

Look for the "Admin Management" section and the "Get admin dashboard statistics" endpoint.

## Future Enhancements

### Potential Additions:
1. **Date Range Filtering**: Statistics for specific time periods
2. **Real-time Updates**: WebSocket integration for live updates
3. **Export Functionality**: CSV/PDF export of statistics
4. **Comparative Analytics**: Month-over-month comparisons
5. **Custom Metrics**: User-defined dashboard widgets

### Example Date Range Implementation:
```java
@GetMapping("/dashboard")
public ResponseEntity<AdminDashboardResponse> getDashboardStatistics(
    @RequestParam(required = false) LocalDate fromDate,
    @RequestParam(required = false) LocalDate toDate) {
    // Implementation with date filtering
}
```

## Benefits

1. **Comprehensive Overview**: Single endpoint provides all key metrics
2. **Real-time Data**: Live statistics from database
3. **Performance Optimized**: Efficient queries and caching ready
4. **Scalable Design**: Easy to extend with additional metrics
5. **Admin Focused**: Designed specifically for administrative use
6. **Well Documented**: Complete API documentation and examples 