package com.demo.demo1.controller;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.demo1.dto.ProjectCreateDTO;
import com.demo.demo1.dto.ProjectResponseDTO;
import com.demo.demo1.dto.ProjectUpdateDTO;
import com.demo.demo1.entity.ProjectStatus;
import com.demo.demo1.service.ProjectService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/insert")
    public ResponseEntity<ProjectResponseDTO> addProject(@Valid @RequestBody ProjectCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(projectService.addProject(request));
    }

    @GetMapping("/")
    public ResponseEntity<Page<ProjectResponseDTO>> getAllProjects(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(projectService.getAllProjectsPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProjectById(@PathVariable Integer id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable Integer id, @Valid @RequestBody ProjectUpdateDTO request) {
        return ResponseEntity.ok(projectService.updateProject(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Integer id) {
        projectService.deleteProject(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<ProjectResponseDTO>> getProjectsByStatus(
            @PathVariable ProjectStatus status,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(projectService.getProjectsByStatusPaged(status, pageable));
    }

    @GetMapping("/dates")
    public ResponseEntity<Page<ProjectResponseDTO>> getProjectsByDateRange(
            @RequestParam LocalDate start,
            @RequestParam LocalDate end,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(projectService.getProjectsByDateRangePaged(start, end, pageable));
    }

    /**
     * Combined search/filter endpoint. All parameters are optional and can be combined,
     * e.g. /api/v1/projects/search?query=platform&status=ACTIVE&engineerId=5
     */
    @GetMapping("/search")
    public ResponseEntity<Page<ProjectResponseDTO>> searchProjects(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) ProjectStatus status,
            @RequestParam(required = false) Integer engineerId,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(projectService.searchProjects(query, status, engineerId, pageable));
    }
}