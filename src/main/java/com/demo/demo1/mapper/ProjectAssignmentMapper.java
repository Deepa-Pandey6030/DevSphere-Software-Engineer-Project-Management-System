package com.demo.demo1.mapper;

import org.springframework.stereotype.Component;
import com.demo.demo1.dto.ProjectAssignmentCreateDTO;
import com.demo.demo1.dto.ProjectAssignmentResponseDTO;
import com.demo.demo1.dto.ProjectAssignmentUpdateWithPutDTO;
import com.demo.demo1.dto.ProjectAssignmentUpdateWithPatchDTO;
import com.demo.demo1.entity.ProjectAssignment;

@Component
public class ProjectAssignmentMapper {

    public ProjectAssignment toEntity(ProjectAssignmentCreateDTO dto) {
        ProjectAssignment assignment = new ProjectAssignment();
        assignment.setProjectRole(dto.getProjectRole());
        assignment.setAllocationPercentage(dto.getAllocationPercentage());
        assignment.setAssignmentStartDate(dto.getAssignmentStartDate());
        assignment.setAssignmentEndDate(dto.getAssignmentEndDate());
        return assignment;
    }

    public void updateEntity(ProjectAssignment assignment, ProjectAssignmentUpdateWithPutDTO dto) {
        assignment.setProjectRole(dto.getProjectRole());
        assignment.setAllocationPercentage(dto.getAllocationPercentage());
        assignment.setAssignmentStartDate(dto.getAssignmentStartDate());
        assignment.setAssignmentEndDate(dto.getAssignmentEndDate());
    }

    public void updateEntity(ProjectAssignment assignment, ProjectAssignmentUpdateWithPatchDTO dto) {
        if (dto.getProjectRole() != null) assignment.setProjectRole(dto.getProjectRole());
        if (dto.getAllocationPercentage() != null) assignment.setAllocationPercentage(dto.getAllocationPercentage());
        if (dto.getAssignmentStartDate() != null) assignment.setAssignmentStartDate(dto.getAssignmentStartDate());
        if (dto.getAssignmentEndDate() != null) assignment.setAssignmentEndDate(dto.getAssignmentEndDate());
    }

    public ProjectAssignmentResponseDTO toResponseDTO(ProjectAssignment assignment) {
        ProjectAssignmentResponseDTO dto = new ProjectAssignmentResponseDTO();
        dto.setId(assignment.getId());
        if (assignment.getEngineer() != null) {
            dto.setEngineerId(assignment.getEngineer().getId());
            dto.setEngineerName(assignment.getEngineer().getFirstName() + " " + assignment.getEngineer().getLastName());
        }
        if (assignment.getProject() != null) {
            dto.setProjectId(assignment.getProject().getId());
            dto.setProjectName(assignment.getProject().getName());
        }
        dto.setProjectRole(assignment.getProjectRole());
        dto.setAllocationPercentage(assignment.getAllocationPercentage());
        dto.setAssignmentStartDate(assignment.getAssignmentStartDate());
        dto.setAssignmentEndDate(assignment.getAssignmentEndDate());
        return dto;
    }
}
