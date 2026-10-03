package com.demo.demo1.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.demo1.dto.CertificationCreateDTO;
import com.demo.demo1.dto.CertificationResponseDTO;
import com.demo.demo1.dto.CertificationUpdateDTO;
import com.demo.demo1.service.CertificationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/certifications")
public class CertificationController {
    private final CertificationService certificationService;

    public CertificationController(CertificationService certificationService) {
        this.certificationService = certificationService;
    }

    @PostMapping("/insert")
    public ResponseEntity<CertificationResponseDTO> addCertification(@Valid @RequestBody CertificationCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(certificationService.addCertification(request));
    }

    @GetMapping("/")
    public ResponseEntity<List<CertificationResponseDTO>> getAllCertifications() {
        return ResponseEntity.ok(certificationService.getAllCertifications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificationResponseDTO> getCertificationById(@PathVariable Integer id) {
        return ResponseEntity.ok(certificationService.getCertificationById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CertificationResponseDTO> updateCertification(@PathVariable Integer id, @Valid @RequestBody CertificationUpdateDTO request) {
        return ResponseEntity.ok(certificationService.updateCertification(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertification(@PathVariable Integer id) {
        certificationService.deleteCertification(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/engineer/{engineerId}")
    public ResponseEntity<List<CertificationResponseDTO>> getCertificationsByEngineer(@PathVariable Integer engineerId) {
        return ResponseEntity.ok(certificationService.getCertificationsByEngineer(engineerId));
    }
}
