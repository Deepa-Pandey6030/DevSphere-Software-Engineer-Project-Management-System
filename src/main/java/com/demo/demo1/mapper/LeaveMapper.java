package com.demo.demo1.mapper;

import org.springframework.stereotype.Component;

import com.demo.demo1.dto.LeaveCreateDTO;
import com.demo.demo1.dto.LeaveResponseDTO;
import com.demo.demo1.dto.LeaveUpdateWithPatchDTO;
import com.demo.demo1.dto.LeaveUpdateWithPutDTO;
import com.demo.demo1.entity.Leave;
import com.demo.demo1.entity.LeaveStatus;

@Component
public class LeaveMapper {

    public Leave toEntity(LeaveCreateDTO dto) {
        Leave leave = new Leave();
        leave.setLeaveType(dto.getLeaveType());
        leave.setStartDate(dto.getStartDate());
        leave.setEndDate(dto.getEndDate());
        leave.setReason(dto.getReason());
        leave.setStatus(LeaveStatus.PENDING); // Default status
        return leave;
    }

    public void updateEntity(Leave leave, LeaveUpdateWithPutDTO dto) {
        leave.setLeaveType(dto.getLeaveType());
        leave.setStartDate(dto.getStartDate());
        leave.setEndDate(dto.getEndDate());
        leave.setReason(dto.getReason());
    }

    public void updateEntity(Leave leave, LeaveUpdateWithPatchDTO dto) {
        if (dto.getLeaveType() != null) leave.setLeaveType(dto.getLeaveType());
        if (dto.getStartDate() != null) leave.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) leave.setEndDate(dto.getEndDate());
        if (dto.getReason() != null) leave.setReason(dto.getReason());
    }

    public LeaveResponseDTO toResponseDTO(Leave leave) {
        LeaveResponseDTO dto = new LeaveResponseDTO();
        dto.setId(leave.getId());
        if (leave.getEngineer() != null) {
            dto.setEngineerId(leave.getEngineer().getId());
            dto.setEngineerName(leave.getEngineer().getFirstName() + " " + leave.getEngineer().getLastName());
        }
        dto.setLeaveType(leave.getLeaveType());
        dto.setStartDate(leave.getStartDate());
        dto.setEndDate(leave.getEndDate());
        dto.setReason(leave.getReason());
        dto.setStatus(leave.getStatus());
        return dto;
    }
}