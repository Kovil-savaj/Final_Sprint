# Station List API Documentation

## Overview
This document describes the new API endpoint that provides distinct source and destination stations for the Train Ticket Management System.

## API Endpoint

### Get All Distinct Stations
**Endpoint:** `GET /api/v1/trains/stations`

**Description:** Retrieves all distinct source and destination stations available in the system. This endpoint is useful for populating dropdown lists in the frontend for station selection.

**URL:** `http://localhost:8081/api/v1/trains/stations`

**Method:** GET

**Headers:**
- `Content-Type: application/json`
- `Accept: application/json`

**Response Format:**
```json
{
    "sourceStations": [
        "Ahmedabad",
        "Bangalore", 
        "Delhi",
        "Hyderabad",
        "Jaipur",
        "Kolkata",
        "Lucknow",
        "Mumbai",
        "Pune"
    ],
    "destinationStations": [
        "Ahmedabad",
        "Bangalore",
        "Chennai",
        "Delhi",
        "Hyderabad",
        "Jaipur",
        "Kolkata",
        "Lucknow",
        "Mumbai",
        "Pune"
    ]
}
```

**Response Fields:**
- `sourceStations`: Array of distinct source station names (sorted alphabetically)
- `destinationStations`: Array of distinct destination station names (sorted alphabetically)

**HTTP Status Codes:**
- `200 OK`: Successfully retrieved station lists
- `500 Internal Server Error`: Server error occurred

## Implementation Details

### Files Modified/Created:

1. **StationListResponse.java** (New DTO)
   - Location: `src/main/java/com/tcs/trainTicketManagementSystem/train/dto/StationListResponse.java`
   - Purpose: Response DTO for station lists

2. **TrainRepository.java** (Modified)
   - Added methods:
     - `findDistinctSourceStations()`: Gets all distinct source stations
     - `findDistinctDestinationStations()`: Gets all distinct destination stations

3. **TrainService.java** (Modified)
   - Added method: `getDistinctStations()`: Service interface method

4. **TrainServiceImpl.java** (Modified)
   - Implemented `getDistinctStations()` method

5. **TrainController.java** (Modified)
   - Added endpoint: `GET /api/v1/trains/stations`

### Database Queries:
```sql
-- Get distinct source stations
SELECT DISTINCT t.source FROM Train t ORDER BY t.source

-- Get distinct destination stations  
SELECT DISTINCT t.destination FROM Train t ORDER BY t.destination
```

## Usage Examples

### cURL Example:
```bash
curl -X GET "http://localhost:8081/api/v1/trains/stations" \
     -H "accept: application/json"
```

### JavaScript/Frontend Example:
```javascript
fetch('/api/v1/trains/stations')
  .then(response => response.json())
  .then(data => {
    console.log('Source stations:', data.sourceStations);
    console.log('Destination stations:', data.destinationStations);
    
    // Populate dropdown lists
    populateDropdown('sourceSelect', data.sourceStations);
    populateDropdown('destinationSelect', data.destinationStations);
  })
  .catch(error => console.error('Error:', error));
```

### React Example:
```jsx
import { useState, useEffect } from 'react';

function StationSelector() {
  const [stations, setStations] = useState({ sourceStations: [], destinationStations: [] });
  
  useEffect(() => {
    fetch('/api/v1/trains/stations')
      .then(response => response.json())
      .then(data => setStations(data));
  }, []);
  
  return (
    <div>
      <select>
        {stations.sourceStations.map(station => (
          <option key={station} value={station}>{station}</option>
        ))}
      </select>
      
      <select>
        {stations.destinationStations.map(station => (
          <option key={station} value={station}>{station}</option>
        ))}
      </select>
    </div>
  );
}
```

## Benefits

1. **Frontend Integration**: Easy to integrate with frontend frameworks for dropdown/select components
2. **Performance**: Efficient database queries with DISTINCT and ORDER BY
3. **Consistency**: Returns sorted lists for better user experience
4. **Reusability**: Can be used across multiple frontend components
5. **Maintainability**: Clean separation of concerns with proper DTO structure

## Testing

The API has been tested with the sample data and returns:
- **9 distinct source stations**
- **10 distinct destination stations**

All stations are returned in alphabetical order for consistent user experience.

## Swagger Documentation

The endpoint is also documented in Swagger UI and can be accessed at:
`http://localhost:8081/swagger-ui/index.html`

Look for the "Train Management" section and the "Get all distinct stations" endpoint. 