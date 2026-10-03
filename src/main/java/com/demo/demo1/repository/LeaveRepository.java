package com.demo.demo1.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.demo1.entity.Leave;
import com.demo.demo1.entity.LeaveStatus;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Integer> {
    List<Leave> findByEngineerId(Integer engineerId);
    List<Leave> findByStatus(LeaveStatus status);

    /**
     * True if the engineer has a leave of the given status whose [startDate, endDate]
     * range covers the given date. Used for the availability check (status = APPROVED).
     */
    boolean existsByEngineerIdAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Integer engineerId, LeaveStatus status, LocalDate onOrAfterStartDate, LocalDate onOrBeforeEndDate);
}