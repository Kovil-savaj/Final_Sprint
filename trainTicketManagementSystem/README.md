# 🚆 Train Ticket Management System

A comprehensive Spring Boot application for managing train tickets with user management, built using H2 database and JPA.

## 📋 Table of Contents

- [Features](#features)
- [Technology Stack](#technology-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Validation Rules](#validation-rules)
- [Error Handling](#error-handling)

## ✨ Features

- **User Management**: Complete CRUD operations for users
- **Role-based Access**: Support for USER and ADMIN roles
- **Input Validation**: Comprehensive validation for all inputs
- **Error Handling**: Proper exception handling with JSON responses
- **API Documentation**: Swagger/OpenAPI documentation
- **Database**: H2 in-memory database with schema.sql
- **Logging**: Comprehensive logging throughout the application

## 🛠 Technology Stack

- **Framework**: Spring Boot 3.5.0
- **Database**: H2 Database
- **ORM**: Spring Data JPA
- **Validation**: Bean Validation (Hibernate Validator)
- **Documentation**: Swagger/OpenAPI 3
- **Java Version**: 17
- **Build Tool**: Maven

## 📁 Project Structure

```
src/main/java/com/tcs/trainTicketManagementSystem/
├── TrainTicketManagementSystemApplication.java
├── users/
│   ├── controller/
│   │   ├── UserController.java
│   │   └── TestController.java
│   ├── dto/
│   │   ├── UserRegistrationRequest.java
│   │   └── UserResponse.java
│   ├── exception/
│   │   ├── UserNotFoundException.java
│   │   ├── UserAlreadyExistsException.java
│   │   └── GlobalExceptionHandler.java
│   ├── model/
│   │   ├── User.java
│   │   └── UserRole.java
│   ├── repository/
│   │   └── UserRepository.java
│   └── service/
│       ├── UserService.java
│       └── UserServiceImpl.java
└── resources/
    ├── application.properties
    └── schema.sql
```

## 🚀 Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd trainTicketManagementSystem
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

4. **Access the application**
   - Application: http://localhost:8761
   - H2 Console: http://localhost:8761/h2-console
   - Swagger UI: http://localhost:8761/swagger-ui.html

### H2 Database Configuration

- **JDBC URL**: `jdbc:h2:mem:traindb`
- **Username**: `sa`
- **Password**: `password`

## 📚 API Documentation

### Base URL
```
http://localhost:8761/api/v1/users
```

### Available Endpoints

#### User Management

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/register` | Register a new user |
| GET | `/{userId}` | Get user by ID |
| GET | `/username/{username}` | Get user by username |
| GET | `/email/{email}` | Get user by email |
| GET | `/` | Get all users |
| GET | `/role/{role}` | Get users by role |
| GET | `/search/username?username={pattern}` | Search users by username |
| GET | `/search/email?email={pattern}` | Search users by email |
| PUT | `/{userId}` | Update user |
| DELETE | `/{userId}` | Delete user |
| GET | `/exists/username/{username}` | Check if username exists |
| GET | `/exists/email/{email}` | Check if email exists |

#### Test Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/test/health` | Health check |
| GET | `/api/test/info` | API information |

### Example API Calls

#### Register a new user
```bash
curl -X POST http://localhost:8761/api/v1/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "John@123",
    "email": "john.doe@email.com",
    "phone": "9876543210",
    "role": "USER"
  }'
```

#### Get user by ID
```bash
curl -X GET http://localhost:8761/api/v1/users/1
```

## 🗄 Database Schema

The application uses the following database tables:

1. **users** - User information and authentication
2. **train** - Train details and routes
3. **train_schedule** - Train schedules by day
4. **fare_type** - Fare information for different classes
5. **booking** - Ticket booking information
6. **passenger** - Passenger details for bookings

See `src/main/resources/schema.sql` for complete schema definition.

## ✅ Validation Rules

### User Registration Validation

- **Username**: 3-50 characters, alphanumeric and underscores only
- **Password**: Minimum 8 characters, must contain:
  - At least one lowercase letter
  - At least one uppercase letter
  - At least one digit
  - At least one special character (@$!%*?&)
- **Email**: Valid email format
- **Phone**: Exactly 10 digits, numbers only
- **Role**: Must be either "USER" or "ADMIN"

## ⚠ Error Handling

The application provides consistent JSON error responses:

```json
{
  "timestamp": "2024-01-01T12:00:00",
  "status": 400,
  "error": "Validation Error",
  "message": "Invalid input data: {field: error}",
  "path": "User API"
}
```

### Common HTTP Status Codes

- **200**: Success
- **201**: Created
- **400**: Bad Request (validation errors)
- **404**: Not Found
- **409**: Conflict (user already exists)
- **500**: Internal Server Error

## 🔧 Configuration

Key configuration properties in `application.properties`:

```properties
# Server
server.port=8761

# Database
spring.datasource.url=jdbc:h2:mem:traindb
spring.h2.console.enabled=true

# JPA
spring.jpa.hibernate.ddl-auto=none
spring.sql.init.mode=always

# Logging
logging.level.com.tcs.trainTicketManagementSystem=DEBUG
```

## 📝 Sample Data

The application includes sample data for testing:

- **Admin User**: username: `admin`, password: `Admin@123`
- **Regular User**: username: `john_doe`, password: `John@123`
- **Sample Train**: Rajdhani Express (Delhi to Mumbai)
- **Sample Fares**: 1AC, 2AC, and SL classes

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License.

---

**Note**: This is a development version. For production use, consider:
- Implementing proper password encryption
- Adding JWT authentication
- Using a production database
- Implementing comprehensive unit tests
- Adding security headers and CORS configuration 