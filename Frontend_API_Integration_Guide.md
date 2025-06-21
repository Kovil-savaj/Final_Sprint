# 🚂 Frontend API Integration Guide - Train Management System

## 📋 Table of Contents
1. [Base URL & Headers](#base-url--headers)
2. [User Management APIs](#user-management-apis)
3. [Train Management APIs](#train-management-apis)
4. [Authentication](#authentication)
5. [Error Handling](#error-handling)
6. [Important Notes for Frontend](#important-notes-for-frontend)

---

## 🌐 Base URL & Headers

```javascript
const BASE_URL = "http://localhost:8081/api/v1";
const HEADERS = {
  "Content-Type": "application/json"
};
```

---

## 👤 User Management APIs

### 1. User Registration
**Endpoint:** `POST /api/v1/users/register`

```json
{
  "username": "john_doe",
  "email": "john@example.com",
  "password": "SecurePass123!",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+91-9876543210",
  "role": "USER"
}
```

**Response:**
```json
{
  "userId": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+91-9876543210",
  "role": "USER",
  "registrationDate": "2025-01-20T10:30:00"
}
```

### 2. User Login
**Endpoint:** `POST /api/v1/users/login`

```json
{
  "username": "john_doe",
  "password": "SecurePass123!"
}
```

**Response:**
```json
{
  "userId": 1,
  "username": "john_doe",
  "role": "USER",
  "loginStatus": "SUCCESS",
  "message": "Login successful"
}
```

### 3. Password Reset
**Endpoint:** `POST /api/v1/users/reset-password`

```json
{
  "email": "john@example.com",
  "newPassword": "NewSecurePass456!"
}
```

---

## 🚂 Train Management APIs

### 1. Create New Train
**Endpoint:** `POST /api/v1/trains`

```json
{
  "trainName": "Express Train 123",
  "source": "Delhi",
  "destination": "Mumbai",
  "departureTime": "08:30:00",
  "arrivalTime": "20:45:00",
  "status": "ACTIVE",
  "scheduleDays": ["MON", "WED", "FRI"],
  "fareTypes": [
    {
      "classType": "1AC",
      "price": 2500.00,
      "seatsAvailable": 50
    },
    {
      "classType": "2AC", 
      "price": 1500.00,
      "seatsAvailable": 100
    },
    {
      "classType": "SL",
      "price": 800.00,
      "seatsAvailable": 200
    }
  ]
}
```

### 2. Search Trains (MOST IMPORTANT FOR FRONTEND)
**Endpoint:** `POST /api/v1/trains/search`

#### Basic Search
```json
{
  "source": "Delhi",
  "destination": "Mumbai"
}
```

#### Advanced Search with Date
```json
{
  "source": "Delhi",
  "destination": "Mumbai",
  "journeyDate": "2025-06-21"
}
```

#### Search with Time Range
```json
{
  "source": "Delhi",
  "destination": "Mumbai",
  "departureTimeAfter": "08:00:00",
  "departureTimeBefore": "18:00:00"
}
```

#### Search with Status
```json
{
  "source": "Delhi",
  "destination": "Mumbai",
  "status": "ACTIVE"
}
```

#### Search with Day of Week
```json
{
  "source": "Delhi",
  "destination": "Mumbai",
  "dayOfWeek": "MON"
}
```

#### Complex Search (All Criteria)
```json
{
  "source": "Delhi",
  "destination": "Mumbai",
  "journeyDate": "2025-06-21",
  "departureTimeAfter": "08:00:00",
  "departureTimeBefore": "18:00:00",
  "status": "ACTIVE",
  "trainName": "Express",
  "dayOfWeek": "MON"
}
```

**Response Format:**
```json
[
  {
    "trainId": 1,
    "trainName": "Rajdhani Express",
    "source": "Delhi",
    "destination": "Mumbai",
    "departureTime": "08:00:00",
    "arrivalTime": "20:00:00",
    "status": "ACTIVE",
    "scheduleDays": ["FRI", "MON", "WED"],
    "fareTypes": [
      {
        "fareTypeId": 1,
        "trainId": 1,
        "classType": "1AC",
        "price": 2500.00,
        "seatsAvailable": 50
      },
      {
        "fareTypeId": 2,
        "trainId": 1,
        "classType": "2AC",
        "price": 1500.00,
        "seatsAvailable": 100
      }
    ]
  }
]
```

### 3. Update Train
**Endpoint:** `PUT /api/v1/trains/{trainId}`

```json
{
  "trainName": "Updated Express Train",
  "source": "Delhi",
  "destination": "Mumbai",
  "departureTime": "09:00:00",
  "arrivalTime": "21:00:00",
  "status": "ACTIVE"
}
```

### 4. Update Train Status
**Endpoint:** `PATCH /api/v1/trains/{trainId}/status`

```json
{
  "status": "MAINTENANCE"
}
```

### 5. Add Fare Type to Train
**Endpoint:** `POST /api/v1/trains/{trainId}/fare-types`

```json
{
  "classType": "3AC",
  "price": 1200.00,
  "seatsAvailable": 150
}
```

### 6. Update Fare Type
**Endpoint:** `PUT /api/v1/trains/{trainId}/fare-types/{fareTypeId}`

```json
{
  "classType": "2AC",
  "price": 1800.00,
  "seatsAvailable": 80
}
```

### 7. Add Schedule Day to Train
**Endpoint:** `POST /api/v1/trains/{trainId}/schedule-days`

```json
{
  "dayOfWeek": "SAT"
}
```

---

## 🔐 Authentication

### Login Flow
1. User submits login form
2. Store user info in session/localStorage
3. Include user role in subsequent requests

```javascript
// After successful login
localStorage.setItem('user', JSON.stringify({
  userId: 1,
  username: 'john_doe',
  role: 'USER'
}));
```

---

## ⚠️ Error Handling

### Common Error Responses
```json
{
  "timestamp": "2025-01-20T10:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "details": ["Source cannot be empty", "Destination cannot be empty"]
}
```

### HTTP Status Codes
- `200` - Success
- `201` - Created
- `400` - Bad Request (Validation errors)
- `404` - Not Found
- `409` - Conflict (Already exists)
- `500` - Internal Server Error

---

## 🎯 Important Notes for Frontend Team

### 1. Date & Time Formats
- **Date:** `YYYY-MM-DD` (e.g., "2025-06-21")
- **Time:** `HH:mm:ss` (e.g., "08:30:00")
- **DateTime:** `YYYY-MM-DDTHH:mm:ss` (e.g., "2025-06-21T08:30:00")

### 2. Enum Values

#### Train Status
- `"ACTIVE"`
- `"INACTIVE"`
- `"MAINTENANCE"`

#### Day of Week (3-letter format)
- `"MON"` (Monday)
- `"TUE"` (Tuesday)
- `"WED"` (Wednesday)
- `"THU"` (Thursday)
- `"FRI"` (Friday)
- `"SAT"` (Saturday)
- `"SUN"` (Sunday)

#### Class Types
- `"1AC"` (First AC)
- `"2AC"` (Second AC)
- `"3AC"` (Third AC)
- `"SL"` (Sleeper)
- `"Seat"` (General Seat)
- `"Sleeper-AC"` (AC Sleeper)
- `"Sleeper-NonAC"` (Non-AC Sleeper)

#### User Roles
- `"USER"`
- `"ADMIN"`

### 3. Search API Behavior
- **All fields are optional** in search
- **AND logic** - all provided criteria must match
- **Case-insensitive** for all text fields (source, destination, train name)
- **Partial matching** for train names

### 4. Case-Insensitive Search (NEW FEATURE!)
**✅ All text fields are now case-insensitive!**

#### Examples that work the same:
```json
// These all return the same results:
{"source": "Delhi"}
{"source": "delhi"}
{"source": "DELHI"}
{"source": "DeLhI"}

// Same for destinations:
{"destination": "Mumbai"}
{"destination": "mumbai"}
{"destination": "MUMBAI"}

// And train names:
{"trainName": "Express"}
{"trainName": "express"}
{"trainName": "EXPRESS"}
```

#### User Authentication (also case-insensitive):
```json
// These work the same:
{"username": "john_doe"}
{"username": "JOHN_DOE"}
{"username": "John_Doe"}

{"email": "john@example.com"}
{"email": "JOHN@EXAMPLE.COM"}
```

### 5. Validation Rules

#### User Registration
- Username: 3-20 characters, alphanumeric + underscore
- Email: Valid email format
- Password: 8+ characters, at least one uppercase, lowercase, digit, special char
- Phone: Valid phone number format

#### Train Creation
- Train name: Required, 2-50 characters
- Source/Destination: Required, 2-50 characters
- Times: Valid time format
- Status: Must be valid enum value
- Schedule days: Array of valid day codes
- Fare types: At least one required

### 6. Frontend Implementation Tips

#### Search Form Example
```javascript
const searchTrains = async (searchCriteria) => {
  try {
    const response = await fetch(`${BASE_URL}/trains/search`, {
      method: 'POST',
      headers: HEADERS,
      body: JSON.stringify(searchCriteria)
    });
    
    if (!response.ok) {
      throw new Error(`HTTP error! status: ${response.status}`);
    }
    
    const trains = await response.json();
    return trains;
  } catch (error) {
    console.error('Search failed:', error);
    throw error;
  }
};

// Usage - case doesn't matter!
const criteria = {
  source: "delhi",        // Works same as "Delhi"
  destination: "MUMBAI",  // Works same as "Mumbai"
  journeyDate: "2025-06-21",
  status: "ACTIVE"
};

const results = await searchTrains(criteria);
```

#### Form Validation Example
```javascript
const validateSearchForm = (formData) => {
  const errors = [];
  
  if (!formData.source && !formData.destination && !formData.journeyDate) {
    errors.push("At least one search criteria is required");
  }
  
  if (formData.journeyDate) {
    const date = new Date(formData.journeyDate);
    if (isNaN(date.getTime())) {
      errors.push("Invalid date format");
    }
  }
  
  if (formData.departureTimeAfter && formData.departureTimeBefore) {
    const after = formData.departureTimeAfter;
    const before = formData.departureTimeBefore;
    if (after >= before) {
      errors.push("Departure time after must be before departure time before");
    }
  }
  
  return errors;
};
```

### 7. Sample Frontend Components

#### Search Component
```javascript
const TrainSearch = () => {
  const [searchCriteria, setSearchCriteria] = useState({});
  const [trains, setTrains] = useState([]);
  const [loading, setLoading] = useState(false);
  
  const handleSearch = async () => {
    setLoading(true);
    try {
      const results = await searchTrains(searchCriteria);
      setTrains(results);
    } catch (error) {
      // Handle error
    } finally {
      setLoading(false);
    }
  };
  
  return (
    <div>
      {/* Search form fields */}
      <button onClick={handleSearch} disabled={loading}>
        {loading ? 'Searching...' : 'Search Trains'}
      </button>
      
      {/* Results display */}
      {trains.map(train => (
        <TrainCard key={train.trainId} train={train} />
      ))}
    </div>
  );
};
```

### 8. Testing Checklist
- [ ] All search criteria combinations work
- [ ] Case-insensitive search works for all text fields
- [ ] Date/time validation works
- [ ] Error messages display correctly
- [ ] Loading states work
- [ ] Empty results handled
- [ ] Form validation works
- [ ] Responsive design
- [ ] Accessibility features

---

## 📞 Support

For any questions or issues:
1. Check the error response details
2. Verify enum values match exactly
3. Ensure date/time formats are correct
4. Test with minimal criteria first
5. Check network tab for actual request/response

**Remember:** 
- The search API is the most critical for user experience - ensure it works smoothly with all combinations of criteria!
- **Case-insensitive search is now implemented** - users can type "delhi", "Delhi", or "DELHI" and get the same results! 