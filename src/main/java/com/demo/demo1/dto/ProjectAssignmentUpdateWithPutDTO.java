package com.demo.demo1.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProjectAssignmentUpdateWithPutDTO {
    @NotNull(message="Engineer ID is required")
    @Positive(message="Engineer ID must be positive")
    private Integer engineerId;
    
    @NotNull(message="Project ID is required")
    @Positive(message="Project ID must be positive")
    private Integer projectId;
    
    @NotNull(message="Project role is required")
    private String projectRole;
    
    @NotNull(message="Allocation percentage is required")
    @Min(value=0, message="Allocation percentage must be at least 0")
    @Max(value=100, message="Allocation percentage must be at most 100")
    private Double allocationPercentage;
    
    @NotNull(message="Assignment start date is required")
    private LocalDate assignmentStartDate;
    
    private LocalDate assignmentEndDate;

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectRole() {
        return projectRole;
    }

    public void setProjectRole(String projectRole) {
        this.projectRole = projectRole;
    }

    public Double getAllocationPercentage() {
        return allocationPercentage;
    }

    public void setAllocationPercentage(Double allocationPercentage) {
        this.allocationPercentage = allocationPercentage;
    }

    public LocalDate getAssignmentStartDate() {
        return assignmentStartDate;
    }

    public void setAssignmentStartDate(LocalDate assignmentStartDate) {
        this.assignmentStartDate = assignmentStartDate;
    }

    public LocalDate getAssignmentEndDate() {
        return assignmentEndDate;
    }

    public void setAssignmentEndDate(LocalDate assignmentEndDate) {
        this.assignmentEndDate = assignmentEndDate;
    }
}