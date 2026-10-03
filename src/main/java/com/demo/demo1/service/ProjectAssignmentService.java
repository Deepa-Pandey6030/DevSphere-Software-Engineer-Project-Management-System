package com.demo.demo1.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.demo1.dto.ProjectAssignmentCreateDTO;
import com.demo.demo1.dto.ProjectAssignmentResponseDTO;
import com.demo.demo1.dto.ProjectAssignmentUpdateWithPatchDTO;
import com.demo.demo1.dto.ProjectAssignmentUpdateWithPutDTO;
import com.demo.demo1.entity.ProjectAssignment;
import com.demo.demo1.exception.ProjectAssignmentNotFoundException;
import com.demo.demo1.exception.ProjectNotFoundException;
import com.demo.demo1.exception.SoftwareEngineerNotFoundException;
import com.demo.demo1.mapper.ProjectAssignmentMapper;
import com.demo.demo1.repository.ProjectAssignmentRepository;
import com.demo.demo1.repository.ProjectRepository;
import com.demo.demo1.repository.SoftwareEngineerRepository;

@Service
public class ProjectAssignmentService {
    private final ProjectAssignmentRepository assignmentRepository;
    private final ProjectAssignmentMapper assignmentMapper;
    private final SoftwareEngineerRepository softwareEngineerRepository;
    private final ProjectRepository projectRepository;

    public ProjectAssignmentService(ProjectAssignmentRepository assignmentRepository,
                                    ProjectAssignmentMapper assignmentMapper,
                                    SoftwareEngineerRepository softwareEngineerRepository,
                                    ProjectRepository projectRepository) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentMapper = assignmentMapper;
        this.softwareEngineerRepository = softwareEngineerRepository;
        this.projectRepository = projectRepository;
    }

    public ProjectAssignmentResponseDTO addAssignment(ProjectAssignmentCreateDTO dto) {
        LocalDate newStart = dto.getAssignmentStartDate();
        LocalDate newEnd = dto.getAssignmentEndDate();

        if (newEnd != null && newStart.isAfter(newEnd)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        List<ProjectAssignment> existingAssignments = assignmentRepository.findByEngineerId(dto.getEngineerId());

        double overlappingAllocation = dto.getAllocationPercentage();
        for (ProjectAssignment assignment : existingAssignments) {
            boolean overlaps = datesOverlap(newStart, newEnd, assignment.getAssignmentStartDate(), assignment.getAssignmentEndDate());

            if (assignment.getProject().getId().equals(dto.getProjectId()) && overlaps) {
                throw new IllegalArgumentException("Engineer is already assigned to this project during an overlapping period.");
            }

            if (overlaps) {
                overlappingAllocation += assignment.getAllocationPercentage();
            }
        }

        if (overlappingAllocation > 100.0) {
            throw new IllegalArgumentException("Total allocation percentage during this period exceeds 100%");
        }

        ProjectAssignment assignment = assignmentMapper.toEntity(dto);
        assignment.setEngineer(softwareEngineerRepository.findById(dto.getEngineerId())
            .orElseThrow(() -> new SoftwareEngineerNotFoundException("Software Engineer not found with id " + dto.getEngineerId())));
        assignment.setProject(projectRepository.findById(dto.getProjectId())
            .orElseThrow(() -> new ProjectNotFoundException("Project not found with id " + dto.getProjectId())));

        ProjectAssignment saved = assignmentRepository.save(assignment);
        return assignmentMapper.toResponseDTO(saved);
    }

    private boolean datesOverlap(LocalDate startA, LocalDate endA, LocalDate startB, LocalDate endB) {
        LocalDate effectiveEndA = endA != null ? endA : LocalDate.MAX;
        LocalDate effectiveEndB = endB != null ? endB : LocalDate.MAX;
        return !startA.isAfter(effectiveEndB) && !startB.isAfter(effectiveEndA);
    }

    public List<ProjectAssignmentResponseDTO> getAllAssignments() {
        return assignmentRepository.findAll().stream()
                .map(assignmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<ProjectAssignmentResponseDTO> getAllAssignmentsPaged(Pageable pageable) {
        return assignmentRepository.findAll(pageable)
                .map(assignmentMapper::toResponseDTO);
    }

    public ProjectAssignmentResponseDTO getAssignmentById(Integer id) {
        ProjectAssignment assignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ProjectAssignmentNotFoundException("Assignment not found with id " + id));
        return assignmentMapper.toResponseDTO(assignment);
    }

    public ProjectAssignmentResponseDTO updateAssignmentPut(Integer id, ProjectAssignmentUpdateWithPutDTO dto) {
        ProjectAssignment existing = assignmentRepository.findById(id)
                .orElseThrow(() -> new ProjectAssignmentNotFoundException("Assignment not found with id " + id));
        assignmentMapper.updateEntity(existing, dto);

        if (dto.getEngineerId() != null) {
            existing.setEngineer(softwareEngineerRepository.findById(dto.getEngineerId())
                .orElseThrow(() -> new SoftwareEngineerNotFoundException("Software Engineer not found with id " + dto.getEngineerId())));
        }
        if (dto.getProjectId() != null) {
            existing.setProject(projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id " + dto.getProjectId())));
        }

        assignmentRepository.save(existing);
        return assignmentMapper.toResponseDTO(existing);
    }

    public ProjectAssignmentResponseDTO updateAssignmentPatch(Integer id, ProjectAssignmentUpdateWithPatchDTO dto) {
        ProjectAssignment existing = assignmentRepository.findById(id)
                .orElseThrow(() -> new ProjectAssignmentNotFoundException("Assignment not found with id " + id));
        assignmentMapper.updateEntity(existing, dto);

        if (dto.getEngineerId() != null) {
            existing.setEngineer(softwareEngineerRepository.findById(dto.getEngineerId())
                .orElseThrow(() -> new SoftwareEngineerNotFoundException("Software Engineer not found with id " + dto.getEngineerId())));
        }
        if (dto.getProjectId() != null) {
            existing.setProject(projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found with id " + dto.getProjectId())));
        }

        assignmentRepository.save(existing);
        return assignmentMapper.toResponseDTO(existing);
    }

    public void deleteAssignment(Integer id) {
        if (!assignmentRepository.existsById(id)) {
            throw new ProjectAssignmentNotFoundException("Assignment not found with id " + id);
        }
        assignmentRepository.deleteById(id);
    }

    public List<ProjectAssignmentResponseDTO> getAssignmentsByEngineer(Integer engineerId) {
        return assignmentRepository.findByEngineerId(engineerId).stream()
                .map(assignmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<ProjectAssignmentResponseDTO> getAssignmentsByEngineerPaged(Integer engineerId, Pageable pageable) {
        return assignmentRepository.findByEngineerId(engineerId, pageable)
                .map(assignmentMapper::toResponseDTO);
    }
}