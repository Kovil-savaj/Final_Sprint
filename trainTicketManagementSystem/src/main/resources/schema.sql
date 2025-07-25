-- Train Ticket Management System Database Schema
-- This file contains all the SQL DDL statements for creating the database tables

-- Drop tables if they exist (for clean startup)
DROP TABLE IF EXISTS passenger;
DROP TABLE IF EXISTS booking;
DROP TABLE IF EXISTS fare_type;
DROP TABLE IF EXISTS train_schedule;
DROP TABLE IF EXISTS train;
DROP TABLE IF EXISTS users;

-- 1. Users table
CREATE TABLE users (
    user_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(15) NOT NULL,
    role VARCHAR(10) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- 2. Train table
CREATE TABLE train (
    train_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    train_name VARCHAR(100) NOT NULL,
    source VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    departure_time TIME NOT NULL,
    journey_hours INT NOT NULL,
    journey_minutes INT NOT NULL,
    status VARCHAR(10) DEFAULT 'ACTIVE'
);

-- 3. TrainSchedule table
CREATE TABLE train_schedule (
    schedule_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    train_id INT NOT NULL,
    day_of_week VARCHAR(3),
    FOREIGN KEY (train_id) REFERENCES train(train_id) ON DELETE CASCADE
);

-- 4. FareType table
CREATE TABLE fare_type (
    fare_type_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    train_id INT NOT NULL,
    class_type VARCHAR(20),
    price DECIMAL(10,2) NOT NULL,
    seats_available INT NOT NULL,
    FOREIGN KEY (train_id) REFERENCES train(train_id) ON DELETE CASCADE
);

-- 5. Booking table
CREATE TABLE booking (
    booking_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_id INT NOT NULL,
    train_id INT NOT NULL,
    fare_type_id INT NOT NULL,
    journey_date DATE NOT NULL,
    booking_date DATE DEFAULT CURRENT_DATE,
    total_fare DECIMAL(10,2) NOT NULL,
    status VARCHAR(15) DEFAULT 'CONFIRMED',
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (train_id) REFERENCES train(train_id),
    FOREIGN KEY (fare_type_id) REFERENCES fare_type(fare_type_id)
);

-- 6. Passenger table
CREATE TABLE passenger (
    passenger_id INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    booking_id INT NOT NULL,
    name VARCHAR(100) NOT NULL,
    age INT NOT NULL,
    gender VARCHAR(10),
    id_proof VARCHAR(50) NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES booking(booking_id) ON DELETE CASCADE
);

-- Create indexes for better performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_train_source_destination ON train(source, destination);
CREATE INDEX idx_train_schedule_train_day ON train_schedule(train_id, day_of_week);
CREATE INDEX idx_fare_type_train ON fare_type(train_id);
CREATE INDEX idx_booking_user ON booking(user_id);
CREATE INDEX idx_booking_train ON booking(train_id);
CREATE INDEX idx_booking_journey_date ON booking(journey_date);
CREATE INDEX idx_passenger_booking ON passenger(booking_id);

-- Insert sample data for testing (optional)
-- Sample admin user (password: Admin@123 - BCrypt encrypted)
INSERT INTO users (username, password, email, phone, role) 
VALUES ('admin', '$2a$10$ijxBdk6zLT9LrnJYZ5CmROh0XPSbcOOeMmAbVyn3FAOglsxWgSUTu', 'Admin@12345', '9876543210', 'ADMIN');

-- Sample regular user (password: User@12345 - BCrypt encrypted)

INSERT INTO users (username, password, email, phone, role) 
VALUES ('user', '$2a$10$/Mg49.uOPE72cKtVCMgxC..Dy635hOmi07DBRbCC7JSWFnGGiIslm', 'user@gmail.com', '9876543211', 'USER');

-- Sample train
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) 
VALUES ('Rajdhani Express', 'Delhi', 'Mumbai', '08:00:00', 12, 0, 'ACTIVE');

-- Sample train schedule
INSERT INTO train_schedule (train_id, day_of_week) 
VALUES (1, 'MON'), (1, 'WED'), (1, 'FRI');

-- Sample fare types
INSERT INTO fare_type (train_id, class_type, price, seats_available) 
VALUES (1, '1AC', 2500.00, 50), (1, '2AC', 1500.00, 100), (1, 'SL', 800.00, 200);

-- Additional sample trains for comprehensive testing
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Garib Rath 100', 'Pune', 'Bangalore', '18:15:00', 6, 45, 'ACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Vande Bharat 101', 'Lucknow', 'Kolkata', '05:00:00', 6, 45, 'ACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Tejas Express 102', 'Ahmedabad', 'Jaipur', '19:30:00', 5, 15, 'INACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Double Decker 103', 'Lucknow', 'Ahmedabad', '20:45:00', 3, 45, 'ACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Vande Bharat 104', 'Pune', 'Jaipur', '11:30:00', 10, 0, 'ACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Rajdhani Express 105', 'Lucknow', 'Pune', '05:15:00', 9, 30, 'ACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Jan Shatabdi 106', 'Delhi', 'Ahmedabad', '16:30:00', 6, 0, 'ACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Jan Shatabdi 107', 'Mumbai', 'Lucknow', '04:00:00', 10, 45, 'ACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Intercity Express 108', 'Delhi', 'Jaipur', '21:00:00', 3, 15, 'ACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Garib Rath 109', 'Pune', 'Kolkata', '08:15:00', 5, 30, 'INACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Shatabdi Express 110', 'Hyderabad', 'Ahmedabad', '16:30:00', 6, 0, 'INACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Humsafar Express 111', 'Kolkata', 'Hyderabad', '03:45:00', 9, 15, 'ACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Jan Shatabdi 112', 'Hyderabad', 'Kolkata', '14:00:00', 9, 45, 'ACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Double Decker 113', 'Bangalore', 'Mumbai', '23:15:00', 7, 15, 'ACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Intercity Express 114', 'Ahmedabad', 'Mumbai', '15:00:00', 7, 15, 'INACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Garib Rath 115', 'Pune', 'Kolkata', '20:15:00', 4, 30, 'INACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Duronto Express 116', 'Jaipur', 'Delhi', '10:00:00', 9, 15, 'ACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Intercity Express 117', 'Kolkata', 'Chennai', '11:00:00', 9, 15, 'ACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Humsafar Express 118', 'Delhi', 'Lucknow', '00:45:00', 7, 0, 'ACTIVE');
INSERT INTO train (train_name, source, destination, departure_time, journey_hours, journey_minutes, status) VALUES ('Duronto Express 119', 'Mumbai', 'Bangalore', '03:30:00', 7, 15, 'ACTIVE');

-- Additional fare types for comprehensive testing
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (2, '2AC', 1286, 99);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (2, 'SL', 2168, 107);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (3, '2AC', 1054, 105);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (3, 'Sleeper-AC', 1751, 53);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (4, '2AC', 1932, 138);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (4, '1AC', 1609, 160);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (5, 'Sleeper-AC', 2149, 106);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (5, 'Seat', 1356, 194);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (6, '1AC', 2520, 185);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (6, '3AC', 2075, 81);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (7, 'SL', 459, 77);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (7, 'SL', 1658, 185);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (8, 'Seat', 796, 149);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (8, '1AC', 1287, 113);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (9, 'SL', 1588, 200);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (9, 'Seat', 2048, 126);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (10, '3AC', 1608, 193);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (10, 'SL', 1564, 131);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (11, '2AC', 2549, 174);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (11, '3AC', 2891, 122);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (12, 'Sleeper-AC', 924, 90);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (12, '2AC', 1916, 56);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (13, '1AC', 2642, 140);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (13, 'Sleeper-NonAC', 2333, 144);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (14, '1AC', 2586, 150);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (14, 'Seat', 2917, 103);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (15, '3AC', 2943, 82);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (15, 'SL', 1840, 72);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (16, 'Sleeper-NonAC', 992, 129);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (16, '3AC', 2645, 61);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (17, 'SL', 1610, 69);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (17, 'Sleeper-AC', 320, 129);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (18, '1AC', 333, 156);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (18, 'Sleeper-NonAC', 340, 98);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (19, 'Sleeper-NonAC', 557, 135);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (19, 'SL', 2231, 184);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (20, 'Seat', 2113, 61);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (20, '1AC', 1522, 161);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (21, 'Sleeper-AC', 550, 181);
INSERT INTO fare_type (train_id, class_type, price, seats_available) VALUES (21, 'Sleeper-NonAC', 2953, 126);

-- Additional train schedules for comprehensive testing
INSERT INTO train_schedule (train_id, day_of_week) VALUES (2, 'SAT');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (2, 'MON');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (2, 'FRI');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (3, 'WED');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (3, 'THU');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (3, 'SUN');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (4, 'SUN');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (4, 'MON');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (5, 'WED');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (5, 'TUE');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (5, 'MON');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (6, 'SUN');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (6, 'FRI');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (7, 'WED');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (7, 'TUE');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (7, 'MON');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (8, 'SAT');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (8, 'TUE');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (8, 'SUN');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (9, 'WED');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (9, 'SAT');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (9, 'MON');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (10, 'SAT');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (10, 'FRI');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (11, 'WED');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (11, 'THU');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (11, 'FRI');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (12, 'SAT');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (13, 'TUE');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (13, 'SUN');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (13, 'MON');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (14, 'THU');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (14, 'MON');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (14, 'FRI');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (15, 'SUN');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (16, 'SAT');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (16, 'TUE');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (16, 'THU');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (17, 'THU');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (17, 'SUN');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (17, 'MON');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (18, 'SAT');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (18, 'WED');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (18, 'FRI');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (19, 'SAT');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (19, 'TUE');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (19, 'THU');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (20, 'WED');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (20, 'TUE');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (20, 'FRI');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (21, 'TUE');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (21, 'SUN');
INSERT INTO train_schedule (train_id, day_of_week) VALUES (21, 'FRI');

-- Sample booking data with Indian names and cities
-- Booking 1: Kovil Kumar & Priya Sharma - Delhi to Mumbai
INSERT INTO booking (user_id, train_id, fare_type_id, journey_date, booking_date, total_fare, status) 
VALUES (2, 1, 2, '2025-06-25', '2025-06-22', 3000.00, 'CONFIRMED');

-- Booking 2: Harshit Patel - Lucknow to Kolkata
INSERT INTO booking (user_id, train_id, fare_type_id, journey_date, booking_date, total_fare, status) 
VALUES (2, 3, 6, '2025-06-26', '2025-06-22', 2108.00, 'CONFIRMED');

-- Booking 3: Himanshu Singh & Jyoti Verma - Pune to Bangalore
INSERT INTO booking (user_id, train_id, fare_type_id, journey_date, booking_date, total_fare, status) 
VALUES (1, 2, 4, '2025-06-27', '2025-06-22', 2572.00, 'CONFIRMED');

-- Booking 4: Shubh Gupta & Esha Reddy - Lucknow to Ahmedabad
INSERT INTO booking (user_id, train_id, fare_type_id, journey_date, booking_date, total_fare, status) 
VALUES (2, 5, 10, '2025-06-28', '2025-06-22', 4298.00, 'CONFIRMED');

-- Booking 5: Jainam Shah - Pune to Jaipur
INSERT INTO booking (user_id, train_id, fare_type_id, journey_date, booking_date, total_fare, status) 
VALUES (1, 6, 12, '2025-06-29', '2025-06-22', 5040.00, 'CONFIRMED');

-- Booking 6: Aarav Mehta & Zara Khan - Lucknow to Pune
INSERT INTO booking (user_id, train_id, fare_type_id, journey_date, booking_date, total_fare, status) 
VALUES (2, 7, 14, '2025-06-30', '2025-06-22', 918.00, 'CONFIRMED');

-- Booking 7: Riya Malhotra - Delhi to Ahmedabad
INSERT INTO booking (user_id, train_id, fare_type_id, journey_date, booking_date, total_fare, status) 
VALUES (1, 8, 16, '2025-07-01', '2025-06-22', 1592.00, 'CONFIRMED');

-- Booking 8: Vikram Joshi & Ananya Desai - Mumbai to Lucknow
INSERT INTO booking (user_id, train_id, fare_type_id, journey_date, booking_date, total_fare, status) 
VALUES (2, 9, 18, '2025-07-02', '2025-06-22', 3176.00, 'CONFIRMED');

-- Booking 9: Aditya Rao - Delhi to Jaipur
INSERT INTO booking (user_id, train_id, fare_type_id, journey_date, booking_date, total_fare, status) 
VALUES (1, 10, 20, '2025-07-03', '2025-06-22', 3216.00, 'CONFIRMED');

-- Booking 10: Neha Iyer & Rahul Nair - Kolkata to Hyderabad
INSERT INTO booking (user_id, train_id, fare_type_id, journey_date, booking_date, total_fare, status) 
VALUES (2, 13, 26, '2025-07-04', '2025-06-22', 5284.00, 'CONFIRMED');

-- Booking 11: Kavya Menon - Hyderabad to Kolkata
INSERT INTO booking (user_id, train_id, fare_type_id, journey_date, booking_date, total_fare, status) 
VALUES (1, 14, 28, '2025-07-05', '2025-06-22', 5172.00, 'CONFIRMED');

-- Booking 12: Arjun Kapoor & Mira Patel - Bangalore to Mumbai
INSERT INTO booking (user_id, train_id, fare_type_id, journey_date, booking_date, total_fare, status) 
VALUES (2, 15, 30, '2025-07-06', '2025-06-22', 5886.00, 'CONFIRMED');

-- Booking 13: Siddharth Agarwal - Jaipur to Delhi
INSERT INTO booking (user_id, train_id, fare_type_id, journey_date, booking_date, total_fare, status) 
VALUES (1, 18, 36, '2025-07-07', '2025-06-22', 666.00, 'CONFIRMED');

-- Booking 14: Tanvi Sharma & Rohan Mehra - Delhi to Lucknow
INSERT INTO booking (user_id, train_id, fare_type_id, journey_date, booking_date, total_fare, status) 
VALUES (2, 20, 40, '2025-07-08', '2025-06-22', 4226.00, 'CONFIRMED');

-- Sample passenger data for all bookings
-- Passengers for Booking 1
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (1, 'Kovil Kumar', 25, 'MALE', '123456789001');
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (1, 'Priya Sharma', 23, 'FEMALE', '123456789002');

-- Passengers for Booking 2
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (2, 'Harshit Patel', 28, 'MALE', '123456789003');

-- Passengers for Booking 3
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (3, 'Himanshu Singh', 30, 'MALE', '123456789004');
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (3, 'Jyoti Verma', 27, 'FEMALE', '123456789005');

-- Passengers for Booking 4
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (4, 'Shubh Gupta', 24, 'MALE', '123456789006');
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (4, 'Esha Reddy', 26, 'FEMALE', '123456789007');

-- Passengers for Booking 5
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (5, 'Jainam Shah', 29, 'MALE', '123456789008');

-- Passengers for Booking 6
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (6, 'Aarav Mehta', 22, 'MALE', '123456789009');
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (6, 'Zara Khan', 25, 'FEMALE', '123456789010');

-- Passengers for Booking 7
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (7, 'Riya Malhotra', 31, 'FEMALE', '123456789011');

-- Passengers for Booking 8
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (8, 'Vikram Joshi', 33, 'MALE', '123456789012');
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (8, 'Ananya Desai', 28, 'FEMALE', '123456789013');

-- Passengers for Booking 9
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (9, 'Aditya Rao', 26, 'MALE', '123456789014');

-- Passengers for Booking 10
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (10, 'Neha Iyer', 24, 'FEMALE', '123456789015');
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (10, 'Rahul Nair', 27, 'MALE', '123456789016');

-- Passengers for Booking 11
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (11, 'Kavya Menon', 29, 'FEMALE', '123456789017');

-- Passengers for Booking 12
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (12, 'Arjun Kapoor', 32, 'MALE', '123456789018');
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (12, 'Mira Patel', 25, 'FEMALE', '123456789019');

-- Passengers for Booking 13
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (13, 'Siddharth Agarwal', 35, 'MALE', '123456789020');

-- Passengers for Booking 14
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (14, 'Tanvi Sharma', 23, 'FEMALE', '123456789021');
INSERT INTO passenger (booking_id, name, age, gender, id_proof) VALUES (14, 'Rohan Mehra', 26, 'MALE', '123456789022');


