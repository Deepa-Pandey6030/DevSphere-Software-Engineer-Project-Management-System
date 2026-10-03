package com.demo.demo1.dto;

import java.time.LocalDate;

/**
 * Answers: "is this engineer available on this date, and how much capacity do they have?"
 * Combines Leave (approved time off) and ProjectAssignment (workload) data.
 */
public class EngineerAvailabilityDTO {
    private Integer engineerId;
    private String engineerName;
    private LocalDate date;
    private boolean onApprovedLeave;
    private double allocatedPercentage;
    private double availableCapacityPercentage;
    private boolean available;

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isOnApprovedLeave() {
        return onApprovedLeave;
    }

    public void setOnApprovedLeave(boolean onApprovedLeave) {
        this.onApprovedLeave = onApprovedLeave;
    }

    public double getAllocatedPercentage() {
        return allocatedPercentage;
    }

    public void setAllocatedPercentage(double allocatedPercentage) {
        this.allocatedPercentage = allocatedPercentage;
    }

    public double getAvailableCapacityPercentage() {
        return availableCapacityPercentage;
    }

    public void setAvailableCapacityPercentage(double availableCapacityPercentage) {
        this.availableCapacityPercentage = availableCapacityPercentage;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}