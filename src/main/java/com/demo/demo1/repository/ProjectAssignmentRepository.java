package com.demo.demo1.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.demo.demo1.entity.ProjectAssignment;

@Repository
public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, Integer> {
    List<ProjectAssignment> findByEngineerId(Integer engineerId);
    List<ProjectAssignment> findByProjectId(Integer projectId);

    Page<ProjectAssignment> findByEngineerId(Integer engineerId, Pageable pageable);
    Page<ProjectAssignment> findByProjectId(Integer projectId, Pageable pageable);

    /**
     * Sum of allocationPercentage across all assignments of this engineer that cover the
     * given date. An assignment with a null assignmentEndDate is treated as still ongoing.
     * Used for the availability/workload check. Returns 0 (not null) when there are none.
     */
    @Query("SELECT COALESCE(SUM(pa.allocationPercentage), 0) FROM ProjectAssignment pa " +
            "WHERE pa.engineer.id = :engineerId " +
            "AND pa.assignmentStartDate <= :date " +
            "AND (pa.assignmentEndDate IS NULL OR pa.assignmentEndDate >= :date)")
    Double sumAllocationPercentageCoveringDate(@Param("engineerId") Integer engineerId, @Param("date") LocalDate date);
}