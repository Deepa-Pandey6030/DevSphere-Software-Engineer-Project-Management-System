package com.demo.demo1.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public class LeaveUpdateWithPutDTO {
    @NotNull(message="Engineer ID is required")
    @Positive(message="Engineer ID must be positive")
    private Integer engineerId;

    @NotNull(message="Leave type is required")
    private String leaveType;

    @NotNull(message="Start date is required")
    private LocalDate startDate;

    @NotNull(message="End date is required")
    private LocalDate endDate;

    private String reason;

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public String getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(String leaveType) {
        this.leaveType = leaveType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}