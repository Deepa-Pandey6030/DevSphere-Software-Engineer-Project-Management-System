package com.demo.demo1.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.demo1.dto.ProjectAssignmentCreateDTO;
import com.demo.demo1.dto.ProjectAssignmentResponseDTO;
import com.demo.demo1.dto.ProjectAssignmentUpdateWithPatchDTO;
import com.demo.demo1.dto.ProjectAssignmentUpdateWithPutDTO;
import com.demo.demo1.service.ProjectAssignmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/project-assignments")
public class ProjectAssignmentController {
    private final ProjectAssignmentService assignmentService;

    public ProjectAssignmentController(ProjectAssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @PostMapping("/insert")
    public ResponseEntity<ProjectAssignmentResponseDTO> addAssignment(@Valid @RequestBody ProjectAssignmentCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(assignmentService.addAssignment(request));
    }

    @GetMapping("/")
    public ResponseEntity<Page<ProjectAssignmentResponseDTO>> getAllAssignments(
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(assignmentService.getAllAssignmentsPaged(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectAssignmentResponseDTO> getAssignmentById(@PathVariable Integer id) {
        return ResponseEntity.ok(assignmentService.getAssignmentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectAssignmentResponseDTO> updateAssignmentPut(@PathVariable Integer id, @Valid @RequestBody ProjectAssignmentUpdateWithPutDTO request) {
        return ResponseEntity.ok(assignmentService.updateAssignmentPut(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectAssignmentResponseDTO> updateAssignmentPatch(@PathVariable Integer id, @Valid @RequestBody ProjectAssignmentUpdateWithPatchDTO request) {
        return ResponseEntity.ok(assignmentService.updateAssignmentPatch(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Integer id) {
        assignmentService.deleteAssignment(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/engineer/{engineerId}")
    public ResponseEntity<Page<ProjectAssignmentResponseDTO>> getAssignmentsByEngineer(
            @PathVariable Integer engineerId,
            @PageableDefault(size = 20, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(assignmentService.getAssignmentsByEngineerPaged(engineerId, pageable));
    }
}