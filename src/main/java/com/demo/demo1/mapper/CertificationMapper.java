package com.demo.demo1.mapper;

import org.springframework.stereotype.Component;
import com.demo.demo1.dto.CertificationCreateDTO;
import com.demo.demo1.dto.CertificationResponseDTO;
import com.demo.demo1.dto.CertificationUpdateDTO;
import com.demo.demo1.entity.Certification;

@Component
public class CertificationMapper {

    public Certification toEntity(CertificationCreateDTO dto) {
        Certification cert = new Certification();
        cert.setCertificationName(dto.getCertificationName());
        cert.setIssuingOrganization(dto.getIssuingOrganization());
        cert.setIssueDate(dto.getIssueDate());
        cert.setExpiryDate(dto.getExpiryDate());
        return cert;
    }

    public void updateEntity(Certification cert, CertificationUpdateDTO dto) {
        if (dto.getCertificationName() != null) cert.setCertificationName(dto.getCertificationName());
        if (dto.getIssuingOrganization() != null) cert.setIssuingOrganization(dto.getIssuingOrganization());
        if (dto.getIssueDate() != null) cert.setIssueDate(dto.getIssueDate());
        if (dto.getExpiryDate() != null) cert.setExpiryDate(dto.getExpiryDate());
    }

    public CertificationResponseDTO toResponseDTO(Certification cert) {
        CertificationResponseDTO dto = new CertificationResponseDTO();
        dto.setId(cert.getId());
        if (cert.getEngineer() != null) {
            dto.setEngineerId(cert.getEngineer().getId());
            dto.setEngineerName(cert.getEngineer().getFirstName() + " " + cert.getEngineer().getLastName());
        }
        dto.setCertificationName(cert.getCertificationName());
        dto.setIssuingOrganization(cert.getIssuingOrganization());
        dto.setIssueDate(cert.getIssueDate());
        dto.setExpiryDate(cert.getExpiryDate());
        return dto;
    }
}
