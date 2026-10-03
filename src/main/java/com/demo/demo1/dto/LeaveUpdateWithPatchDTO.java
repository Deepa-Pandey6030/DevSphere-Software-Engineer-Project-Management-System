package com.demo.demo1.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Positive;

/**
 * Partial update of a leave request's details.
 * Status is intentionally NOT editable here - it is only changed via the
 * dedicated approve/reject endpoints, which enforce valid status transitions.
 * Only a PENDING leave may be updated (enforced in the service layer).
 */
public class LeaveUpdateWithPatchDTO {
    @Positive(message="Engineer ID must be positive")
    private Integer engineerId;

    private String leaveType;
    private LocalDate startDate;
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