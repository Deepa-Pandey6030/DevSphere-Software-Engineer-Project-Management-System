package com.demo.demo1.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.demo.demo1.dto.ProjectCreateDTO;
import com.demo.demo1.dto.ProjectResponseDTO;
import com.demo.demo1.dto.ProjectUpdateDTO;
import com.demo.demo1.entity.Project;
import com.demo.demo1.entity.ProjectStatus;
import com.demo.demo1.exception.ProjectNotFoundException;
import com.demo.demo1.mapper.ProjectMapper;
import com.demo.demo1.repository.ProjectRepository;
import com.demo.demo1.repository.spec.ProjectSpecifications;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    public ProjectService(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectRepository = projectRepository;
        this.projectMapper = projectMapper;
    }

    public ProjectResponseDTO addProject(ProjectCreateDTO dto) {
        Project project = projectMapper.toEntity(dto);
        Project saved = projectRepository.save(project);
        return projectMapper.toResponseDTO(saved);
    }

    public List<ProjectResponseDTO> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(projectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<ProjectResponseDTO> getAllProjectsPaged(Pageable pageable) {
        return projectRepository.findAll(pageable)
                .map(projectMapper::toResponseDTO);
    }

    public ProjectResponseDTO getProjectById(Integer id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id " + id));
        return projectMapper.toResponseDTO(project);
    }

    public ProjectResponseDTO updateProject(Integer id, ProjectUpdateDTO dto) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id " + id));

        projectMapper.updateEntity(existingProject, dto);
        projectRepository.save(existingProject);
        return projectMapper.toResponseDTO(existingProject);
    }

    public void deleteProject(Integer id) {
        if (!projectRepository.existsById(id)) {
            throw new ProjectNotFoundException("Project not found with id " + id);
        }
        projectRepository.deleteById(id);
    }

    public List<ProjectResponseDTO> getProjectsByStatus(ProjectStatus status) {
        return projectRepository.findByStatus(status).stream()
                .map(projectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<ProjectResponseDTO> getProjectsByStatusPaged(ProjectStatus status, Pageable pageable) {
        return projectRepository.findByStatus(status, pageable)
                .map(projectMapper::toResponseDTO);
    }

    public List<ProjectResponseDTO> getProjectsByDateRange(LocalDate start, LocalDate end) {
        return projectRepository.findByStartDateBetween(start, end).stream()
                .map(projectMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<ProjectResponseDTO> getProjectsByDateRangePaged(LocalDate start, LocalDate end, Pageable pageable) {
        return projectRepository.findByStartDateBetween(start, end, pageable)
                .map(projectMapper::toResponseDTO);
    }

    /**
     * Combined, database-side search across projects. Every criterion is optional;
     * omitted (null/blank) criteria are simply not applied as filters.
     */
    public Page<ProjectResponseDTO> searchProjects(String query, ProjectStatus status, Integer engineerId, Pageable pageable) {
        Specification<Project> spec = Specification.where((Specification<Project>) null);

        if (query != null && !query.isBlank()) {
            spec = spec.and(ProjectSpecifications.hasNameOrCodeContaining(query));
        }
        if (status != null) {
            spec = spec.and(ProjectSpecifications.hasStatus(status));
        }
        if (engineerId != null) {
            spec = spec.and(ProjectSpecifications.hasEngineerAssigned(engineerId));
        }

        return projectRepository.findAll(spec, pageable)
                .map(projectMapper::toResponseDTO);
    }
}