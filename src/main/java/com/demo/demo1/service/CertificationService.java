package com.demo.demo1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.demo.demo1.dto.CertificationCreateDTO;
import com.demo.demo1.dto.CertificationResponseDTO;
import com.demo.demo1.dto.CertificationUpdateDTO;
import com.demo.demo1.entity.Certification;
import com.demo.demo1.exception.CertificationNotFoundException;
import com.demo.demo1.exception.SoftwareEngineerNotFoundException;
import com.demo.demo1.mapper.CertificationMapper;
import com.demo.demo1.repository.CertificationRepository;
import com.demo.demo1.repository.SoftwareEngineerRepository;

@Service
public class CertificationService {
    private final CertificationRepository certificationRepository;
    private final CertificationMapper certificationMapper;
    private final SoftwareEngineerRepository softwareEngineerRepository;

    public CertificationService(CertificationRepository certificationRepository,
                                CertificationMapper certificationMapper,
                                SoftwareEngineerRepository softwareEngineerRepository) {
        this.certificationRepository = certificationRepository;
        this.certificationMapper = certificationMapper;
        this.softwareEngineerRepository = softwareEngineerRepository;
    }

    public CertificationResponseDTO addCertification(CertificationCreateDTO dto) {
        Certification cert = certificationMapper.toEntity(dto);
        cert.setEngineer(softwareEngineerRepository.findById(dto.getEngineerId())
            .orElseThrow(() -> new SoftwareEngineerNotFoundException("Software Engineer not found with id " + dto.getEngineerId())));

        Certification saved = certificationRepository.save(cert);
        return certificationMapper.toResponseDTO(saved);
    }

    public List<CertificationResponseDTO> getAllCertifications() {
        return certificationRepository.findAll().stream()
                .map(certificationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CertificationResponseDTO getCertificationById(Integer id) {
        Certification cert = certificationRepository.findById(id)
                .orElseThrow(() -> new CertificationNotFoundException("Certification not found with id " + id));
        return certificationMapper.toResponseDTO(cert);
    }

    public CertificationResponseDTO updateCertification(Integer id, CertificationUpdateDTO dto) {
        Certification existing = certificationRepository.findById(id)
                .orElseThrow(() -> new CertificationNotFoundException("Certification not found with id " + id));
        certificationMapper.updateEntity(existing, dto);

        certificationRepository.save(existing);
        return certificationMapper.toResponseDTO(existing);
    }

    public void deleteCertification(Integer id) {
        if (!certificationRepository.existsById(id)) {
            throw new CertificationNotFoundException("Certification not found with id " + id);
        }
        certificationRepository.deleteById(id);
    }

    public List<CertificationResponseDTO> getCertificationsByEngineer(Integer engineerId) {
        return certificationRepository.findByEngineerId(engineerId).stream()
                .map(certificationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}