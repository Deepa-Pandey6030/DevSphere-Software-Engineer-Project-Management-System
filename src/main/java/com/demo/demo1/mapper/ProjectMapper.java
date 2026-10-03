package com.demo.demo1.mapper;

import org.springframework.stereotype.Component;
import java.util.UUID;
import com.demo.demo1.dto.ProjectCreateDTO;
import com.demo.demo1.dto.ProjectResponseDTO;
import com.demo.demo1.dto.ProjectUpdateDTO;
import com.demo.demo1.entity.Project;

@Component
public class ProjectMapper {

    public Project toEntity(ProjectCreateDTO dto) {
        Project project = new Project();
        project.setpCode("PRJ-" + UUID.randomUUID());
        project.setName(dto.getName());
        project.setDesc(dto.getDescription());
        project.setStatus(dto.getStatus());
        project.setStartDate(dto.getStartDate());
        project.setEndDate(dto.getEndDate());
        project.setClientInfo(dto.getClientInfo());
        return project;
    }

    public void updateEntity(Project project, ProjectUpdateDTO dto) {
        if (dto.getName() != null) project.setName(dto.getName());
        if (dto.getDescription() != null) project.setDesc(dto.getDescription());
        if (dto.getStatus() != null) project.setStatus(dto.getStatus());
        if (dto.getStartDate() != null) project.setStartDate(dto.getStartDate());
        if (dto.getEndDate() != null) project.setEndDate(dto.getEndDate());
        if (dto.getClientInfo() != null) project.setClientInfo(dto.getClientInfo());
    }

    public ProjectResponseDTO toResponseDTO(Project project) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.setId(project.getId());
        dto.setpCode(project.getpCode());
        dto.setName(project.getName());
        dto.setDescription(project.getDesc());
        dto.setStatus(project.getStatus());
        dto.setStartDate(project.getStartDate());
        dto.setEndDate(project.getEndDate());
        dto.setClientInfo(project.getClientInfo());
        return dto;
    }
}
