package com.tcs.trainTicketManagementSystem.train.repository;

import com.tcs.trainTicketManagementSystem.train.model.TrainSchedule;
import com.tcs.trainTicketManagementSystem.train.model.DayOfWeek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for TrainSchedule entity operations.
 */
@Repository
public interface TrainScheduleRepository extends JpaRepository<TrainSchedule, Long> {

    /**
     * Find schedules by train ID.
     */
    List<TrainSchedule> findByTrainTrainId(Long trainId);

    /**
     * Find schedules by day of week.
     */
    List<TrainSchedule> findByDayOfWeek(DayOfWeek dayOfWeek);

    /**
     * Find schedules by train ID and day of week.
     */
    List<TrainSchedule> findByTrainTrainIdAndDayOfWeek(Long trainId, DayOfWeek dayOfWeek);

    /**
     * Delete schedules by train ID.
     */
    void deleteByTrainTrainId(Long trainId);

    /**
     * Check if schedule exists for train and day.
     */
    boolean existsByTrainTrainIdAndDayOfWeek(Long trainId, DayOfWeek dayOfWeek);
} 