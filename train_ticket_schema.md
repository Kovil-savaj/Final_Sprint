# 🚆 Train Ticket Management System - Database Schema

This document defines the SQL schema for a Train Ticket Management System built using Spring Boot with H2 in a monolithic architecture. It includes support for fare types, multiple passengers per booking, train schedules on specific weekdays, and user roles.

---

## 📄 Table of Contents

1. [Users](#1-users)
2. [Trains](#2-trains)
3. [Train Schedule](#3-train-schedule)
4. [Fare Type](#4-fare-type)
5. [Booking](#5-booking)
6. [Passenger](#6-passenger)

---

## 1. `Users`

```sql
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15),
    role VARCHAR(10) CHECK (role IN ('USER', 'ADMIN')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## 2. `Train`

```sql
CREATE TABLE train (
    train_id INT PRIMARY KEY AUTO_INCREMENT,
    train_name VARCHAR(100) NOT NULL,
    source VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
    status VARCHAR(10) CHECK (status IN ('ACTIVE', 'INACTIVE')) DEFAULT 'ACTIVE'
);
```

---

## 3. `TrainSchedule`

```sql
CREATE TABLE train_schedule (
    schedule_id INT PRIMARY KEY AUTO_INCREMENT,
    train_id INT NOT NULL,
    day_of_week VARCHAR(3) CHECK (day_of_week IN ('MON', 'TUE', 'WED', 'THU', 'FRI', 'SAT', 'SUN')),
    FOREIGN KEY (train_id) REFERENCES train(train_id) ON DELETE CASCADE
);
```

---

## 4. `FareType`

```sql
CREATE TABLE fare_type (
    fare_type_id INT PRIMARY KEY AUTO_INCREMENT,
    train_id INT NOT NULL,
    class_type VARCHAR(20) CHECK (class_type IN ('1AC', '2AC', '3AC', 'SL', 'Sleeper-AC', 'Sleeper-NonAC', 'Seat')),
    price DECIMAL(10,2) NOT NULL,
    seats_available INT NOT NULL,
    FOREIGN KEY (train_id) REFERENCES train(train_id) ON DELETE CASCADE
);
```

---

## 5. `Booking`

```sql
CREATE TABLE booking (
    booking_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    train_id INT NOT NULL,
    fare_type_id INT NOT NULL,
    journey_date DATE NOT NULL,
    booking_date DATE DEFAULT CURRENT_DATE,
    total_fare DECIMAL(10,2) NOT NULL,
    status VARCHAR(15) CHECK (status IN ('CONFIRMED', 'CANCELLED')) DEFAULT 'CONFIRMED',
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (train_id) REFERENCES train(train_id),
    FOREIGN KEY (fare_type_id) REFERENCES fare_type(fare_type_id)
);
```

---

## 6. `Passenger`

```sql
CREATE TABLE passenger (
    passenger_id INT PRIMARY KEY AUTO_INCREMENT,
    booking_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10),
    id_proof VARCHAR(50) NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES booking(booking_id) ON DELETE CASCADE
);
```

---

## 🔐 Notes

- No JWT-based authentication; roles handled via `role` field in `users` table.
- Trains can run on specific days using `train_schedule`.
- Up to 10 passengers can be booked under one `booking_id`.
- `journey_date` in `booking` ensures correct travel date validation.
- Separate `fare_type` table improves query flexibility and pricing logic.

---