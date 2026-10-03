package com.demo.demo1.controller;

import java.util.List;

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

import com.demo.demo1.dto.LeaveCreateDTO;
import com.demo.demo1.dto.LeaveResponseDTO;
import com.demo.demo1.dto.LeaveUpdateWithPatchDTO;
import com.demo.demo1.dto.LeaveUpdateWithPutDTO;
import com.demo.demo1.entity.LeaveStatus;
import com.demo.demo1.service.LeaveService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/leaves")
public class LeaveController {
    private final LeaveService leaveService;

    public LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @PostMapping("/insert")
    public ResponseEntity<LeaveResponseDTO> addLeave(@Valid @RequestBody LeaveCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leaveService.addLeave(request));
    }

    @GetMapping("/")
    public ResponseEntity<List<LeaveResponseDTO>> getAllLeaves() {
        return ResponseEntity.ok(leaveService.getAllLeaves());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveResponseDTO> getLeaveById(@PathVariable Integer id) {
        return ResponseEntity.ok(leaveService.getLeaveById(id));
    }

    /**
     * Full update of a PENDING leave's details (engineer, type, dates, reason).
     * Status cannot be changed here - use /{id}/approve or /{id}/reject.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LeaveResponseDTO> updateLeavePut(@PathVariable Integer id, @Valid @RequestBody LeaveUpdateWithPutDTO request) {
        return ResponseEntity.ok(leaveService.updateLeavePut(id, request));
    }

    /**
     * Partial update of a PENDING leave's details.
     * Status cannot be changed here - use /{id}/approve or /{id}/reject.
     */
    @PatchMapping("/{id}")
    public ResponseEntity<LeaveResponseDTO> updateLeavePatch(@PathVariable Integer id, @Valid @RequestBody LeaveUpdateWithPatchDTO request) {
        return ResponseEntity.ok(leaveService.updateLeavePatch(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLeave(@PathVariable Integer id) {
        leaveService.deleteLeave(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/engineer/{engineerId}")
    public ResponseEntity<List<LeaveResponseDTO>> getLeavesByEngineer(@PathVariable Integer engineerId) {
        return ResponseEntity.ok(leaveService.getLeavesByEngineer(engineerId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<LeaveResponseDTO>> getLeavesByStatus(@PathVariable LeaveStatus status) {
        return ResponseEntity.ok(leaveService.getLeavesByStatus(status));
    }

    /** Approves a PENDING leave. Fails with 409 if the leave is not PENDING. */
    @PostMapping("/{id}/approve")
    public ResponseEntity<LeaveResponseDTO> approveLeave(@PathVariable Integer id) {
        return ResponseEntity.ok(leaveService.approveLeave(id));
    }

    /** Rejects a PENDING leave. Fails with 409 if the leave is not PENDING. */
    @PostMapping("/{id}/reject")
    public ResponseEntity<LeaveResponseDTO> rejectLeave(@PathVariable Integer id) {
        return ResponseEntity.ok(leaveService.rejectLeave(id));
    }
}