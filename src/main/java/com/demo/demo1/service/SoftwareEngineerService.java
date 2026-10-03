package com.demo.demo1.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.demo.demo1.dto.EngineerAvailabilityDTO;
import com.demo.demo1.dto.SoftwareEngineerCreateDTO;
import com.demo.demo1.dto.SoftwareEngineerResponseDTO;
import com.demo.demo1.dto.SoftwareEngineerUpdateWithPatchDTO;
import com.demo.demo1.dto.SoftwareEngineerUpdateWithPutDTO;
import com.demo.demo1.entity.LeaveStatus;
import com.demo.demo1.entity.SoftwareEngineer;
import com.demo.demo1.exception.DepartmentNotFoundException;
import com.demo.demo1.exception.SoftwareEngineerNotFoundException;
import com.demo.demo1.mapper.SoftwareEngineerMapper;
import com.demo.demo1.repository.DepartmentRepository;
import com.demo.demo1.repository.LeaveRepository;
import com.demo.demo1.repository.ProjectAssignmentRepository;
import com.demo.demo1.repository.SoftwareEngineerRepository;
import com.demo.demo1.repository.spec.SoftwareEngineerSpecifications;

@Service
public class SoftwareEngineerService {

private final SoftwareEngineerRepository softwareEngineerRepository;
private final SoftwareEngineerMapper softwareEngineerMapper;
private final DepartmentRepository departmentRepository;
private final LeaveRepository leaveRepository;
private final ProjectAssignmentRepository projectAssignmentRepository;

public SoftwareEngineerService(SoftwareEngineerRepository softwareEngineerRepository, SoftwareEngineerMapper softwareEngineerMapper, DepartmentRepository departmentRepository, LeaveRepository leaveRepository, ProjectAssignmentRepository projectAssignmentRepository) {
    this.softwareEngineerMapper=softwareEngineerMapper;
    this.softwareEngineerRepository = softwareEngineerRepository;
    this.departmentRepository = departmentRepository;
    this.leaveRepository = leaveRepository;
    this.projectAssignmentRepository = projectAssignmentRepository;
}


public SoftwareEngineerResponseDTO addSoftwareEngineer(SoftwareEngineerCreateDTO dto) {
    SoftwareEngineer engineer =softwareEngineerMapper.toEntity(dto);
    engineer.setEmployeeId("EMP-" + UUID.randomUUID());

    if (dto.getDepartmentId() != null) {
        engineer.setDepartment(departmentRepository.findById(dto.getDepartmentId())
            .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id " + dto.getDepartmentId())));
    }
    if (dto.getManagerId() != null) {
        SoftwareEngineer manager = softwareEngineerRepository.findById(dto.getManagerId())
            .orElseThrow(() -> new SoftwareEngineerNotFoundException("Manager not found with id " + dto.getManagerId()));
        engineer.setManager(manager);
    }

    SoftwareEngineer saved =softwareEngineerRepository.save(engineer);
    return softwareEngineerMapper.toResponseDTO(saved);
}

public List<SoftwareEngineerResponseDTO> SoftwareEngineerResponseDTOAll(){
    List<SoftwareEngineer> engineers=getAllSoftwareEngineers();
    List<SoftwareEngineerResponseDTO> responseDTOs=new ArrayList<>();
    for(SoftwareEngineer engineer:engineers){
        SoftwareEngineerResponseDTO responseDTO=softwareEngineerMapper.toResponseDTO(engineer);
        responseDTOs.add(responseDTO);
    }
    return responseDTOs;
}

public Page<SoftwareEngineerResponseDTO> getAllSoftwareEngineersPaged(Pageable pageable){
    return softwareEngineerRepository.findAll(pageable)
        .map(softwareEngineerMapper::toResponseDTO);
}

public SoftwareEngineerResponseDTO SoftwareEngineerResponseDTOById(Integer id){
    SoftwareEngineer engineer=getSoftwareEngineerById(id);
    SoftwareEngineerResponseDTO responseDTO=softwareEngineerMapper.toResponseDTO(engineer);
    return responseDTO;
}


public List<SoftwareEngineer> getAllSoftwareEngineers(){
    return softwareEngineerRepository.findAll();
}

public SoftwareEngineer getSoftwareEngineerById(Integer id){
    return softwareEngineerRepository.findById(id).orElseThrow(()->new SoftwareEngineerNotFoundException("Software Engineer not found with "+id));
}

public void deleteSoftwareEngineerById(Integer id){
    softwareEngineerRepository.findById(id).orElseThrow(()->new SoftwareEngineerNotFoundException("Software Engineer not found with "+id));
    softwareEngineerRepository.deleteById(id);
}

public SoftwareEngineerResponseDTO updateSoftwareEngineerwithPut(Integer id,SoftwareEngineerUpdateWithPutDTO dto){
    SoftwareEngineer existingSoftwareEngineer=softwareEngineerRepository.findById(id).orElseThrow(()->new SoftwareEngineerNotFoundException ("Software Engineer not found with "+id));

    existingSoftwareEngineer= softwareEngineerMapper.updateEntity(existingSoftwareEngineer,dto);

    if (dto.getDepartmentId() != null) {
        existingSoftwareEngineer.setDepartment(departmentRepository.findById(dto.getDepartmentId())
            .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id " + dto.getDepartmentId())));
    }
    if (dto.getManagerId() != null) {
        validateManagerAssignment(id, dto.getManagerId());
        SoftwareEngineer manager = softwareEngineerRepository.findById(dto.getManagerId())
            .orElseThrow(() -> new SoftwareEngineerNotFoundException("Manager not found with id " + dto.getManagerId()));
        existingSoftwareEngineer.setManager(manager);
    }

    softwareEngineerRepository.save(existingSoftwareEngineer);

    SoftwareEngineerResponseDTO responseDTO=softwareEngineerMapper.toResponseDTO(existingSoftwareEngineer);
    return responseDTO;
}

public SoftwareEngineerResponseDTO updateSoftwareEngineerWithPatch(Integer id,SoftwareEngineerUpdateWithPatchDTO dto){
    SoftwareEngineer existingSoftwareEngineer=softwareEngineerRepository.findById(id).orElseThrow(()->new SoftwareEngineerNotFoundException ("Software Engineer not found with "+id));
    existingSoftwareEngineer=softwareEngineerMapper.updateEntity(existingSoftwareEngineer, dto);

    if (dto.getDepartmentId() != null) {
        existingSoftwareEngineer.setDepartment(departmentRepository.findById(dto.getDepartmentId())
            .orElseThrow(() -> new DepartmentNotFoundException("Department not found with id " + dto.getDepartmentId())));
    }
    if (dto.getManagerId() != null) {
        validateManagerAssignment(id, dto.getManagerId());
        SoftwareEngineer manager = softwareEngineerRepository.findById(dto.getManagerId())
            .orElseThrow(() -> new SoftwareEngineerNotFoundException("Manager not found with id " + dto.getManagerId()));
        existingSoftwareEngineer.setManager(manager);
    }

    softwareEngineerRepository.save(existingSoftwareEngineer);

    SoftwareEngineerResponseDTO responseDTO=softwareEngineerMapper.toResponseDTO(existingSoftwareEngineer);
    return responseDTO;
}

private void validateManagerAssignment(Integer engineerId, Integer managerId) {
    if (managerId.equals(engineerId)) {
        throw new IllegalArgumentException("An engineer cannot be their own manager.");
    }

    Integer currentId = managerId;
    while (currentId != null) {
        if (currentId.equals(engineerId)) {
            throw new IllegalArgumentException("This manager assignment would create a reporting cycle.");
        }
        SoftwareEngineer current = softwareEngineerRepository.findById(currentId).orElse(null);
        currentId = (current != null && current.getManager() != null) ? current.getManager().getId() : null;
    }
}

public SoftwareEngineerResponseDTO SoftwareEngineerResponseDTOByEmailId(String email){
    SoftwareEngineer engineer=softwareEngineerRepository.findByEmail(email).orElseThrow(()->new SoftwareEngineerNotFoundException ("Software Engineer not found with "+email));
    SoftwareEngineerResponseDTO responseDTO=softwareEngineerMapper.toResponseDTO(engineer);
    return responseDTO;
}

public SoftwareEngineerResponseDTO SoftwareEngineerResponseDTOByFirstName(String firstName){
    SoftwareEngineer engineer=softwareEngineerRepository.findByFirstName(firstName).orElseThrow(()->new SoftwareEngineerNotFoundException ("Software Engineer not found with "+firstName));
    SoftwareEngineerResponseDTO responseDTO=softwareEngineerMapper.toResponseDTO(engineer);
    return responseDTO;
}

public List<SoftwareEngineerResponseDTO> getEngineersByDepartmentId(Integer departmentId){
    return softwareEngineerRepository.findByDepartmentId(departmentId).stream()
        .map(softwareEngineerMapper::toResponseDTO)
        .collect(Collectors.toList());
}

public Page<SoftwareEngineerResponseDTO> getEngineersByDepartmentIdPaged(Integer departmentId, Pageable pageable){
    return softwareEngineerRepository.findByDepartmentId(departmentId, pageable)
        .map(softwareEngineerMapper::toResponseDTO);
}

public List<SoftwareEngineerResponseDTO> getEngineersByManagerId(Integer managerId){
    return softwareEngineerRepository.findByManagerId(managerId).stream()
        .map(softwareEngineerMapper::toResponseDTO)
        .collect(Collectors.toList());
}

public Page<SoftwareEngineerResponseDTO> getEngineersByManagerIdPaged(Integer managerId, Pageable pageable){
    return softwareEngineerRepository.findByManagerId(managerId, pageable)
        .map(softwareEngineerMapper::toResponseDTO);
}

public List<SoftwareEngineerResponseDTO> getEngineersByDesignation(String designation){
    return softwareEngineerRepository.findByDesignation(designation).stream()
        .map(softwareEngineerMapper::toResponseDTO)
        .collect(Collectors.toList());
}

public Page<SoftwareEngineerResponseDTO> getEngineersByDesignationPaged(String designation, Pageable pageable){
    return softwareEngineerRepository.findByDesignation(designation, pageable)
        .map(softwareEngineerMapper::toResponseDTO);
}

/**
 * Combined, database-side search across engineers. Every criterion is optional;
 * omitted (null/blank) criteria are simply not applied as filters.
 */
public Page<SoftwareEngineerResponseDTO> searchEngineers(
        String name,
        String email,
        Integer departmentId,
        String employmentStatus,
        Integer technologyId,
        Integer projectId,
        Boolean availableOnly,
        LocalDate availableOn,
        Pageable pageable) {

    Specification<SoftwareEngineer> spec = Specification.where((Specification<SoftwareEngineer>) null);

    if (name != null && !name.isBlank()) {
        spec = spec.and(SoftwareEngineerSpecifications.hasNameContaining(name));
    }
    if (email != null && !email.isBlank()) {
        spec = spec.and(SoftwareEngineerSpecifications.hasEmailContaining(email));
    }
    if (departmentId != null) {
        spec = spec.and(SoftwareEngineerSpecifications.hasDepartmentId(departmentId));
    }
    if (employmentStatus != null && !employmentStatus.isBlank()) {
        spec = spec.and(SoftwareEngineerSpecifications.hasEmploymentStatus(employmentStatus));
    }
    if (technologyId != null) {
        spec = spec.and(SoftwareEngineerSpecifications.hasTechnologyId(technologyId));
    }
    if (projectId != null) {
        spec = spec.and(SoftwareEngineerSpecifications.hasProjectId(projectId));
    }
    if (Boolean.TRUE.equals(availableOnly)) {
        LocalDate date = (availableOn != null) ? availableOn : LocalDate.now();
        spec = spec.and(SoftwareEngineerSpecifications.isAvailableOn(date));
    }

    return softwareEngineerRepository.findAll(spec, pageable)
            .map(softwareEngineerMapper::toResponseDTO);
}

/**
 * Availability rule (kept intentionally simple, single source of truth also used by
 * SoftwareEngineerSpecifications.isAvailableOn for bulk search):
 *   - NOT available if the engineer has an APPROVED leave covering the date.
 *   - Otherwise, available if total allocation from ProjectAssignments covering the
 *     date is less than 100%. The remaining capacity is reported alongside the flag,
 *     since "available" in staffing terms is rarely a hard yes/no.
 */
public EngineerAvailabilityDTO checkAvailability(Integer engineerId, LocalDate date) {
    SoftwareEngineer engineer = getSoftwareEngineerById(engineerId);

    boolean onApprovedLeave = leaveRepository
            .existsByEngineerIdAndStatusAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                    engineerId, LeaveStatus.APPROVED, date, date);

    Double sumAllocation = projectAssignmentRepository.sumAllocationPercentageCoveringDate(engineerId, date);
    double allocatedPercentage = (sumAllocation != null) ? sumAllocation : 0.0;

    boolean available = !onApprovedLeave && allocatedPercentage < 100.0;
    double availableCapacity = onApprovedLeave ? 0.0 : Math.max(0.0, 100.0 - allocatedPercentage);

    EngineerAvailabilityDTO dto = new EngineerAvailabilityDTO();
    dto.setEngineerId(engineer.getId());
    dto.setEngineerName(engineer.getFirstName() + " " + engineer.getLastName());
    dto.setDate(date);
    dto.setOnApprovedLeave(onApprovedLeave);
    dto.setAllocatedPercentage(allocatedPercentage);
    dto.setAvailableCapacityPercentage(availableCapacity);
    dto.setAvailable(available);
    return dto;
}

}