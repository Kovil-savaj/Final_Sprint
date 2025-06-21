package com.tcs.trainTicketManagementSystem.train.model;

import jakarta.persistence.*;

/**
 * Entity representing a train schedule for specific days of the week.
 */
@Entity
@Table(name = "train_schedule")
public class TrainSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "train_id", nullable = false)
    private Train train;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false, length = 3)
    private DayOfWeek dayOfWeek;

    // Default constructor
    public TrainSchedule() {}

    // Constructor with fields
    public TrainSchedule(Train train, DayOfWeek dayOfWeek) {
        this.train = train;
        this.dayOfWeek = dayOfWeek;
    }

    // Getters and Setters
    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    public Train getTrain() {
        return train;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public String toString() {
        return "TrainSchedule{" +
                "scheduleId=" + scheduleId +
                ", trainId=" + (train != null ? train.getTrainId() : null) +
                ", dayOfWeek=" + dayOfWeek +
                '}';
    }
} 