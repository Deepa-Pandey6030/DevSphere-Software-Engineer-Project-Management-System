package com.demo.demo1.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.demo.demo1.dto.LeaveCreateDTO;
import com.demo.demo1.dto.LeaveResponseDTO;
import com.demo.demo1.dto.LeaveUpdateWithPatchDTO;
import com.demo.demo1.dto.LeaveUpdateWithPutDTO;
import com.demo.demo1.entity.Leave;
import com.demo.demo1.entity.LeaveStatus;
import com.demo.demo1.entity.SoftwareEngineer;
import com.demo.demo1.exception.LeaveConflictException;
import com.demo.demo1.exception.LeaveNotFoundException;
import com.demo.demo1.exception.SoftwareEngineerNotFoundException;
import com.demo.demo1.mapper.LeaveMapper;
import com.demo.demo1.repository.LeaveRepository;
import com.demo.demo1.repository.SoftwareEngineerRepository;

@Service
public class LeaveService {
    private final LeaveRepository leaveRepository;
    private final LeaveMapper leaveMapper;
    private final SoftwareEngineerRepository softwareEngineerRepository;

    public LeaveService(LeaveRepository leaveRepository, LeaveMapper leaveMapper,
                         SoftwareEngineerRepository softwareEngineerRepository) {
        this.leaveRepository = leaveRepository;
        this.leaveMapper = leaveMapper;
        this.softwareEngineerRepository = softwareEngineerRepository;
    }

    public LeaveResponseDTO addLeave(LeaveCreateDTO dto) {
        validateDateOrder(dto.getStartDate(), dto.getEndDate());

        SoftwareEngineer engineer = softwareEngineerRepository.findById(dto.getEngineerId())
                .orElseThrow(() -> new SoftwareEngineerNotFoundException(
                        "Software Engineer not found with id " + dto.getEngineerId()));

        checkNoOverlap(dto.getEngineerId(), dto.getStartDate(), dto.getEndDate(), null);

        Leave leave = leaveMapper.toEntity(dto);
        leave.setEngineer(engineer);

        Leave saved = leaveRepository.save(leave);
        return leaveMapper.toResponseDTO(saved);
    }

    public List<LeaveResponseDTO> getAllLeaves() {
        return leaveRepository.findAll().stream()
                .map(leaveMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public LeaveResponseDTO getLeaveById(Integer id) {
        return leaveMapper.toResponseDTO(findLeaveOrThrow(id));
    }

    public LeaveResponseDTO updateLeavePut(Integer id, LeaveUpdateWithPutDTO dto) {
        Leave existingLeave = findLeaveOrThrow(id);
        requirePending(existingLeave, "updated");

        validateDateOrder(dto.getStartDate(), dto.getEndDate());

        SoftwareEngineer engineer = softwareEngineerRepository.findById(dto.getEngineerId())
                .orElseThrow(() -> new SoftwareEngineerNotFoundException(
                        "Software Engineer not found with id " + dto.getEngineerId()));

        checkNoOverlap(dto.getEngineerId(), dto.getStartDate(), dto.getEndDate(), existingLeave.getId());

        leaveMapper.updateEntity(existingLeave, dto);
        existingLeave.setEngineer(engineer);

        leaveRepository.save(existingLeave);
        return leaveMapper.toResponseDTO(existingLeave);
    }

    public LeaveResponseDTO updateLeavePatch(Integer id, LeaveUpdateWithPatchDTO dto) {
        Leave existingLeave = findLeaveOrThrow(id);
        requirePending(existingLeave, "updated");

        Integer effectiveEngineerId = (dto.getEngineerId() != null)
                ? dto.getEngineerId() : existingLeave.getEngineer().getId();
        LocalDate effectiveStart = (dto.getStartDate() != null) ? dto.getStartDate() : existingLeave.getStartDate();
        LocalDate effectiveEnd = (dto.getEndDate() != null) ? dto.getEndDate() : existingLeave.getEndDate();

        validateDateOrder(effectiveStart, effectiveEnd);

        SoftwareEngineer engineer = existingLeave.getEngineer();
        if (dto.getEngineerId() != null) {
            engineer = softwareEngineerRepository.findById(dto.getEngineerId())
                    .orElseThrow(() -> new SoftwareEngineerNotFoundException(
                            "Software Engineer not found with id " + dto.getEngineerId()));
        }

        checkNoOverlap(effectiveEngineerId, effectiveStart, effectiveEnd, existingLeave.getId());

        leaveMapper.updateEntity(existingLeave, dto);
        existingLeave.setEngineer(engineer);

        leaveRepository.save(existingLeave);
        return leaveMapper.toResponseDTO(existingLeave);
    }

    public LeaveResponseDTO approveLeave(Integer id) {
        Leave leave = findLeaveOrThrow(id);
        requirePending(leave, "approved");
        leave.setStatus(LeaveStatus.APPROVED);
        leaveRepository.save(leave);
        return leaveMapper.toResponseDTO(leave);
    }

    public LeaveResponseDTO rejectLeave(Integer id) {
        Leave leave = findLeaveOrThrow(id);
        requirePending(leave, "rejected");
        leave.setStatus(LeaveStatus.REJECTED);
        leaveRepository.save(leave);
        return leaveMapper.toResponseDTO(leave);
    }

    public void deleteLeave(Integer id) {
        if (!leaveRepository.existsById(id)) {
            throw new LeaveNotFoundException("Leave not found with id " + id);
        }
        leaveRepository.deleteById(id);
    }

    public List<LeaveResponseDTO> getLeavesByEngineer(Integer engineerId) {
        return leaveRepository.findByEngineerId(engineerId).stream()
                .map(leaveMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<LeaveResponseDTO> getLeavesByStatus(LeaveStatus status) {
        return leaveRepository.findByStatus(status).stream()
                .map(leaveMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // ---------------- internal helpers ----------------

    private Leave findLeaveOrThrow(Integer id) {
        return leaveRepository.findById(id)
                .orElseThrow(() -> new LeaveNotFoundException("Leave not found with id " + id));
    }

    /** Only a PENDING leave may be edited, approved, or rejected. */
    private void requirePending(Leave leave, String action) {
        if (leave.getStatus() != LeaveStatus.PENDING) {
            throw new LeaveConflictException(
                    "Leave " + leave.getId() + " is already " + leave.getStatus()
                            + "; only a PENDING leave can be " + action + ".");
        }
    }

    private void validateDateOrder(LocalDate start, LocalDate end) {
        if (start != null && end != null && start.isAfter(end)) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }
    }

    /**
     * Two closed date ranges [startA, endA] and [startB, endB] overlap
     * if and only if startA <= endB and startB <= endA.
     * Only PENDING/APPROVED leaves of the same engineer count as blocking;
     * REJECTED leaves and the leave currently being updated (excludeLeaveId) are ignored.
     */
    private void checkNoOverlap(Integer engineerId, LocalDate start, LocalDate end, Integer excludeLeaveId) {
        List<Leave> existingLeaves = leaveRepository.findByEngineerId(engineerId);
        for (Leave l : existingLeaves) {
            if (excludeLeaveId != null && l.getId().equals(excludeLeaveId)) {
                continue;
            }
            if (l.getStatus() == LeaveStatus.REJECTED) {
                continue;
            }
            if (datesOverlap(start, end, l.getStartDate(), l.getEndDate())) {
                throw new LeaveConflictException(
                        "Leave dates overlap with an existing " + l.getStatus() + " leave (id " + l.getId()
                                + ", " + l.getStartDate() + " to " + l.getEndDate() + ") for this engineer.");
            }
        }
    }

    private boolean datesOverlap(LocalDate startA, LocalDate endA, LocalDate startB, LocalDate endB) {
        return !startA.isAfter(endB) && !startB.isAfter(endA);
    }
}