# User API Endpoints Documentation

## Base URL
```
http://localhost:8080/api/v1/users
```

## Authentication
Currently, the API does not require authentication tokens. All endpoints are publicly accessible.

---

## 1. User Registration

### Endpoint
```
POST /api/v1/users/register
```

### Description
Creates a new user account with validation.

### Request Body
```json
{
  "username": "string",
  "password": "string",
  "email": "string",
  "phone": "string",
  "role": "USER" | "ADMIN"
}
```

### Field Validation Rules
- **username**: 
  - Required
  - 3-50 characters
  - Only letters, numbers, and underscores allowed
  - Pattern: `^[a-zA-Z0-9_]+$`
- **password**: 
  - Required
  - Minimum 8 characters
  - Must contain at least one lowercase letter, one uppercase letter, one digit, and one special character
  - Pattern: `^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$`
- **email**: 
  - Required
  - Valid email format
- **phone**: 
  - Required
  - Exactly 10 digits
  - Pattern: `^[0-9]{10}$`
- **role**: 
  - Optional (defaults to "USER")
  - Values: "USER" or "ADMIN"

### Success Response (201 Created)
```json
{
  "userId": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "phone": "1234567890",
  "role": "USER",
  "createdAt": "2024-01-15T10:30:00"
}
```

### Error Responses

#### 400 Bad Request - Validation Error
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Validation Error",
  "message": "Invalid input data provided",
  "path": "User API",
  "fieldErrors": {
    "username": "Username must be between 3 and 50 characters",
    "password": "Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character",
    "email": "Please provide a valid email address",
    "phone": "Phone number must be exactly 10 digits"
  }
}
```

#### 409 Conflict - User Already Exists
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 409,
  "error": "User Already Exists",
  "message": "User with username 'john_doe' already exists",
  "path": "User API",
  "fieldErrors": {}
}
```

---

## 2. User Login

### Endpoint
```
POST /api/v1/users/login
```

### Description
Authenticates a user and returns login response.

### Request Body
```json
{
  "usernameOrEmail": "string",
  "password": "string"
}
```

### Field Validation Rules
- **usernameOrEmail**: Required (can be username or email)
- **password**: Required

### Success Response (200 OK)
```json
{
  "authenticated": true,
  "message": "Login successful",
  "userId": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "role": "USER",
  "loginTime": "2024-01-15T10:30:00"
}
```

### Error Response (401 Unauthorized)
```json
{
  "authenticated": false,
  "message": "Invalid username/email or password",
  "userId": null,
  "username": null,
  "email": null,
  "role": null,
  "loginTime": "2024-01-15T10:30:00"
}
```

---

## 3. Get User by ID

### Endpoint
```
GET /api/v1/users/{userId}
```

### Description
Retrieves user information by user ID.

### Path Parameters
- **userId**: Long (required) - The user's unique identifier

### Success Response (200 OK)
```json
{
  "userId": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "phone": "1234567890",
  "role": "USER",
  "createdAt": "2024-01-15T10:30:00"
}
```

### Error Response (404 Not Found)
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "User Not Found",
  "message": "User with ID 999 not found",
  "path": "User API",
  "fieldErrors": {}
}
```

---

## 4. Get User by Username

### Endpoint
```
GET /api/v1/users/username/{username}
```

### Description
Retrieves user information by username.

### Path Parameters
- **username**: String (required) - The user's username

### Success Response (200 OK)
```json
{
  "userId": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "phone": "1234567890",
  "role": "USER",
  "createdAt": "2024-01-15T10:30:00"
}
```

### Error Response (404 Not Found)
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "User Not Found",
  "message": "User with username 'nonexistent' not found",
  "path": "User API",
  "fieldErrors": {}
}
```

---

## 5. Get User by Email

### Endpoint
```
GET /api/v1/users/email/{email}
```

### Description
Retrieves user information by email address.

### Path Parameters
- **email**: String (required) - The user's email address

### Success Response (200 OK)
```json
{
  "userId": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "phone": "1234567890",
  "role": "USER",
  "createdAt": "2024-01-15T10:30:00"
}
```

### Error Response (404 Not Found)
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "User Not Found",
  "message": "User with email 'nonexistent@example.com' not found",
  "path": "User API",
  "fieldErrors": {}
}
```

---

## 6. Get All Users

### Endpoint
```
GET /api/v1/users
```

### Description
Retrieves all users in the system.

### Success Response (200 OK)
```json
[
  {
    "userId": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "phone": "1234567890",
    "role": "USER",
    "createdAt": "2024-01-15T10:30:00"
  },
  {
    "userId": 2,
    "username": "admin_user",
    "email": "admin@example.com",
    "phone": "9876543210",
    "role": "ADMIN",
    "createdAt": "2024-01-15T11:00:00"
  }
]
```

---

## 7. Get Users by Role

### Endpoint
```
GET /api/v1/users/role/{role}
```

### Description
Retrieves all users with a specific role.

### Path Parameters
- **role**: String (required) - The role to filter by ("USER" or "ADMIN")

### Success Response (200 OK)
```json
[
  {
    "userId": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "phone": "1234567890",
    "role": "USER",
    "createdAt": "2024-01-15T10:30:00"
  },
  {
    "userId": 3,
    "username": "jane_doe",
    "email": "jane@example.com",
    "phone": "5555555555",
    "role": "USER",
    "createdAt": "2024-01-15T12:00:00"
  }
]
```

### Error Response (400 Bad Request)
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Invalid Argument",
  "message": "Invalid role: INVALID_ROLE",
  "path": "User API",
  "fieldErrors": {}
}
```

---

## 8. Search Users by Username

### Endpoint
```
GET /api/v1/users/search/username?username={username}
```

### Description
Searches users by username pattern (partial match).

### Query Parameters
- **username**: String (required) - The username pattern to search for

### Success Response (200 OK)
```json
[
  {
    "userId": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "phone": "1234567890",
    "role": "USER",
    "createdAt": "2024-01-15T10:30:00"
  },
  {
    "userId": 4,
    "username": "johnny_bravo",
    "email": "johnny@example.com",
    "phone": "1111111111",
    "role": "USER",
    "createdAt": "2024-01-15T13:00:00"
  }
]
```

---

## 9. Search Users by Email

### Endpoint
```
GET /api/v1/users/search/email?email={email}
```

### Description
Searches users by email pattern (partial match).

### Query Parameters
- **email**: String (required) - The email pattern to search for

### Success Response (200 OK)
```json
[
  {
    "userId": 1,
    "username": "john_doe",
    "email": "john@example.com",
    "phone": "1234567890",
    "role": "USER",
    "createdAt": "2024-01-15T10:30:00"
  },
  {
    "userId": 5,
    "username": "john_admin",
    "email": "john.admin@example.com",
    "phone": "2222222222",
    "role": "ADMIN",
    "createdAt": "2024-01-15T14:00:00"
  }
]
```

---

## 10. Update User

### Endpoint
```
PUT /api/v1/users/{userId}
```

### Description
Updates user information.

### Path Parameters
- **userId**: Long (required) - The user's unique identifier

### Request Body
```json
{
  "username": "string",
  "password": "string",
  "email": "string",
  "phone": "string",
  "role": "USER" | "ADMIN"
}
```

### Field Validation Rules
Same as User Registration (see section 1).

### Success Response (200 OK)
```json
{
  "userId": 1,
  "username": "john_doe_updated",
  "email": "john.updated@example.com",
  "phone": "1234567890",
  "role": "USER",
  "createdAt": "2024-01-15T10:30:00"
}
```

### Error Responses

#### 400 Bad Request - Validation Error
Same format as User Registration validation errors.

#### 404 Not Found
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "User Not Found",
  "message": "User with ID 999 not found",
  "path": "User API",
  "fieldErrors": {}
}
```

#### 409 Conflict - Username or Email Already Exists
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 409,
  "error": "User Already Exists",
  "message": "User with email 'existing@example.com' already exists",
  "path": "User API",
  "fieldErrors": {}
}
```

---

## 11. Reset User Password

### Endpoint
```
PUT /api/v1/users/{userId}/password
```

### Description
Resets the password for a specific user.

### Path Parameters
- **userId**: Long (required) - The user's unique identifier

### Request Body
```json
{
  "newPassword": "string"
}
```

### Field Validation Rules
- **newPassword**: 
  - Required
  - Minimum 8 characters
  - Must contain at least one lowercase letter, one uppercase letter, one digit, and one special character
  - Pattern: `^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$`

### Success Response (200 OK)
```json
{
  "userId": 1,
  "username": "john_doe",
  "email": "john@example.com",
  "phone": "1234567890",
  "role": "USER",
  "createdAt": "2024-01-15T10:30:00"
}
```

### Error Responses

#### 400 Bad Request - Validation Error
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 400,
  "error": "Validation Error",
  "message": "Invalid input data provided",
  "path": "User API",
  "fieldErrors": {
    "newPassword": "Password must contain at least one lowercase letter, one uppercase letter, one digit, and one special character"
  }
}
```

#### 404 Not Found
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "User Not Found",
  "message": "User with ID 999 not found",
  "path": "User API",
  "fieldErrors": {}
}
```

---

## 12. Delete User

### Endpoint
```
DELETE /api/v1/users/{userId}
```

### Description
Deletes a user by ID.

### Path Parameters
- **userId**: Long (required) - The user's unique identifier

### Success Response (204 No Content)
No response body.

### Error Response (404 Not Found)
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 404,
  "error": "User Not Found",
  "message": "User with ID 999 not found",
  "path": "User API",
  "fieldErrors": {}
}
```

---

## 13. Check Username Exists

### Endpoint
```
GET /api/v1/users/exists/username/{username}
```

### Description
Checks if a username already exists.

### Path Parameters
- **username**: String (required) - The username to check

### Success Response (200 OK)
```json
true
```
or
```json
false
```

---

## 14. Check Email Exists

### Endpoint
```
GET /api/v1/users/exists/email/{email}
```

### Description
Checks if an email already exists.

### Path Parameters
- **email**: String (required) - The email to check

### Success Response (200 OK)
```json
true
```
or
```json
false
```

---

## Common Error Responses

### 500 Internal Server Error
```json
{
  "timestamp": "2024-01-15T10:30:00",
  "status": 500,
  "error": "Internal Server Error",
  "message": "An unexpected error occurred. Please try again later.",
  "path": "User API",
  "fieldErrors": {}
}
```

---

## Data Types

### UserRole Enum
- `USER` - Regular user
- `ADMIN` - Administrator user

### Timestamp Format
All timestamps are in ISO 8601 format: `YYYY-MM-DDTHH:mm:ss`

---

## Validation Rules Summary

### Username
- Required
- 3-50 characters
- Only letters, numbers, and underscores
- Pattern: `^[a-zA-Z0-9_]+$`

### Password
- Required
- Minimum 8 characters
- Must contain at least one lowercase letter, one uppercase letter, one digit, and one special character
- Pattern: `^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$`

### Email
- Required
- Valid email format

### Phone
- Required
- Exactly 10 digits
- Pattern: `^[0-9]{10}$`

### Role
- Optional (defaults to "USER")
- Values: "USER" or "ADMIN"

---

## Frontend Integration Notes

1. **Password Requirements**: Ensure frontend validation matches backend password requirements
2. **Phone Format**: Frontend should format phone numbers as 10 digits only
3. **Username Format**: Frontend should prevent special characters except underscores
4. **Error Handling**: Always check the `fieldErrors` object in validation errors for specific field issues
5. **Search Functionality**: Username and email search endpoints support partial matching
6. **Existence Checks**: Use the exists endpoints before registration to provide real-time feedback
7. **Response Handling**: All successful responses include the complete user object (except password)
8. **HTTP Status Codes**: Use appropriate status codes for different scenarios (201 for creation, 204 for deletion, etc.) 