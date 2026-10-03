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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.demo1.dto.EngineerAvailabilityDTO;
import com.demo.demo1.dto.SoftwareEngineerCreateDTO;
import com.demo.demo1.dto.SoftwareEngineerResponseDTO;
import com.demo.demo1.dto.SoftwareEngineerUpdateWithPatchDTO;
import com.demo.demo1.dto.SoftwareEngineerUpdateWithPutDTO;
import com.demo.demo1.service.SoftwareEngineerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/software-engineers")
public class SoftwareEngineerController {
    private final SoftwareEngineerService softwareEngineerService;

    public SoftwareEngineerController(SoftwareEngineerService softwareEngineerService){
        this.softwareEngineerService=softwareEngineerService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<SoftwareEngineerResponseDTO>> getAllEngineers(
            @PageableDefault(size = 20, sort = "id") Pageable pageable){
        Page<SoftwareEngineerResponseDTO> engineers = softwareEngineerService.getAllSoftwareEngineersPaged(pageable);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(engineers);
    }

    @PostMapping("/insert")
    public ResponseEntity<SoftwareEngineerResponseDTO> addEngineer(@Valid @RequestBody SoftwareEngineerCreateDTO request){
        SoftwareEngineerResponseDTO savedEngineer=softwareEngineerService.addSoftwareEngineer(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedEngineer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SoftwareEngineerResponseDTO> getEngineerById(@PathVariable Integer id){
        SoftwareEngineerResponseDTO engineer= softwareEngineerService.SoftwareEngineerResponseDTOById(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(engineer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEngineerById(@PathVariable Integer id){
        softwareEngineerService.deleteSoftwareEngineerById(id);
        return ResponseEntity
            .status(HttpStatus.OK)
            .build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SoftwareEngineerResponseDTO> updateEngineerwithPut(@PathVariable Integer id,@Valid @RequestBody SoftwareEngineerUpdateWithPutDTO request){
        SoftwareEngineerResponseDTO savedResponseDTO=softwareEngineerService.updateSoftwareEngineerwithPut(id,request);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(savedResponseDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SoftwareEngineerResponseDTO> updateEngineerwithPatch(@PathVariable Integer id,@Valid @RequestBody SoftwareEngineerUpdateWithPatchDTO request){
        SoftwareEngineerResponseDTO savedResponseDTO=softwareEngineerService.updateSoftwareEngineerWithPatch(id,request);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(savedResponseDTO);
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<SoftwareEngineerResponseDTO> getEngineerByEmailId(@PathVariable String email){
        SoftwareEngineerResponseDTO savedResponseDTO=softwareEngineerService.SoftwareEngineerResponseDTOByEmailId(email);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(savedResponseDTO);
    }

    @GetMapping("/firstName/{firstName}")
    public ResponseEntity<SoftwareEngineerResponseDTO> getEngineerByfirstName(@PathVariable String firstName){
        SoftwareEngineerResponseDTO savedResponseDTO=softwareEngineerService.SoftwareEngineerResponseDTOByFirstName(firstName);
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(savedResponseDTO);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<Page<SoftwareEngineerResponseDTO>> getEngineersByDepartment(
            @PathVariable Integer departmentId,
            @PageableDefault(size = 20, sort = "id") Pageable pageable){
        Page<SoftwareEngineerResponseDTO> engineers = softwareEngineerService.getEngineersByDepartmentIdPaged(departmentId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(engineers);
    }

    @GetMapping("/manager/{managerId}")
    public ResponseEntity<Page<SoftwareEngineerResponseDTO>> getEngineersByManager(
            @PathVariable Integer managerId,
            @PageableDefault(size = 20, sort = "id") Pageable pageable){
        Page<SoftwareEngineerResponseDTO> engineers = softwareEngineerService.getEngineersByManagerIdPaged(managerId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(engineers);
    }

    @GetMapping("/designation/{designation}")
    public ResponseEntity<Page<SoftwareEngineerResponseDTO>> getEngineersByDesignation(
            @PathVariable String designation,
            @PageableDefault(size = 20, sort = "id") Pageable pageable){
        Page<SoftwareEngineerResponseDTO> engineers = softwareEngineerService.getEngineersByDesignationPaged(designation, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(engineers);
    }

    /**
     * Combined search/filter endpoint. All parameters are optional and can be combined,
     * e.g. /api/v1/software-engineers/search?name=john&departmentId=2&availableOnly=true
     */
    @GetMapping("/search")
    public ResponseEntity<Page<SoftwareEngineerResponseDTO>> searchEngineers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer departmentId,
            @RequestParam(required = false) String employmentStatus,
            @RequestParam(required = false) Integer technologyId,
            @RequestParam(required = false) Integer projectId,
            @RequestParam(required = false) Boolean availableOnly,
            @RequestParam(required = false) LocalDate availableOn,
            @PageableDefault(size = 20, sort = "id") Pageable pageable){
        Page<SoftwareEngineerResponseDTO> engineers = softwareEngineerService.searchEngineers(
                name, email, departmentId, employmentStatus, technologyId, projectId, availableOnly, availableOn, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(engineers);
    }

    /**
     * Availability of a single engineer on a date (defaults to today).
     * Combines approved leave + current project-assignment allocation.
     * e.g. /api/v1/software-engineers/5/availability?date=2026-09-15
     */
    @GetMapping("/{id}/availability")
    public ResponseEntity<EngineerAvailabilityDTO> checkAvailability(
            @PathVariable Integer id,
            @RequestParam(required = false) LocalDate date){
        LocalDate targetDate = (date != null) ? date : LocalDate.now();
        return ResponseEntity.ok(softwareEngineerService.checkAvailability(id, targetDate));
    }
}