package com.demo.demo1.mapper;

import org.springframework.stereotype.Component;

import com.demo.demo1.dto.SoftwareEngineerCreateDTO;
import com.demo.demo1.dto.SoftwareEngineerResponseDTO;
import com.demo.demo1.dto.SoftwareEngineerUpdateWithPatchDTO;
import com.demo.demo1.dto.SoftwareEngineerUpdateWithPutDTO;
import com.demo.demo1.entity.SoftwareEngineer;

@Component
public class SoftwareEngineerMapper {

    public SoftwareEngineer toEntity(SoftwareEngineerCreateDTO dto){
        SoftwareEngineer engineer=new SoftwareEngineer();
        engineer.setFirstName(dto.getFirstName());
        engineer.setLastName(dto.getLastName());
        engineer.setEmail(dto.getEmail());
        engineer.setPhoneNumber(dto.getPhoneNumber());
        engineer.setDateOfBirth(dto.getDateOfBirth());        
        engineer.setJoiningDate(dto.getJoiningDate());
        engineer.setDesignation(dto.getDesignation());
        engineer.setEmploymentStatus(dto.getEmploymentStatus());
        engineer.setLocation(dto.getLocation());
        engineer.setExperience(dto.getExperience());
        return engineer;
    }

    public SoftwareEngineer updateEntity(SoftwareEngineer engineer ,SoftwareEngineerUpdateWithPutDTO dto){
        engineer.setFirstName(dto.getFirstName());
        engineer.setLastName(dto.getLastName());
        engineer.setEmail(dto.getEmail());
        engineer.setPhoneNumber(dto.getPhoneNumber());
        engineer.setDateOfBirth(dto.getDateOfBirth());        
        engineer.setJoiningDate(dto.getJoiningDate());
        engineer.setDesignation(dto.getDesignation());
        engineer.setEmploymentStatus(dto.getEmploymentStatus());
        engineer.setLocation(dto.getLocation());
        engineer.setExperience(dto.getExperience());
        return engineer;
    }

    public SoftwareEngineer updateEntity(SoftwareEngineer engineer,SoftwareEngineerUpdateWithPatchDTO dto) {
        if (dto.getFirstName() != null) engineer.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) engineer.setLastName(dto.getLastName());
        if (dto.getEmail() != null) engineer.setEmail(dto.getEmail());
        if (dto.getPhoneNumber() != null) engineer.setPhoneNumber(dto.getPhoneNumber());
        if (dto.getDateOfBirth() != null) engineer.setDateOfBirth(dto.getDateOfBirth());
        if (dto.getJoiningDate() != null) engineer.setJoiningDate(dto.getJoiningDate());
        if (dto.getDesignation() != null) engineer.setDesignation(dto.getDesignation());
        if (dto.getEmploymentStatus() != null) engineer.setEmploymentStatus(dto.getEmploymentStatus());
        if (dto.getLocation() != null) engineer.setLocation(dto.getLocation());
        if (dto.getExperience() != null) engineer.setExperience(dto.getExperience());
        return engineer;
    }

    public SoftwareEngineerResponseDTO toResponseDTO(SoftwareEngineer engineer){
        SoftwareEngineerResponseDTO responseDTO=new SoftwareEngineerResponseDTO();
        responseDTO.setId(engineer.getId());
        responseDTO.setEmployeeId(engineer.getEmployeeId());
        responseDTO.setFirstName(engineer.getFirstName()); 
        responseDTO.setLastName(engineer.getLastName());
        responseDTO.setEmail(engineer.getEmail());
        responseDTO.setPhoneNumber(engineer.getPhoneNumber());
        responseDTO.setDateOfBirth(engineer.getDateOfBirth());
        responseDTO.setJoiningDate(engineer.getJoiningDate());
        responseDTO.setDesignation(engineer.getDesignation());
        responseDTO.setEmploymentStatus(engineer.getEmploymentStatus());
        responseDTO.setLocation(engineer.getLocation());
        responseDTO.setExperience(engineer.getExperience());
        
        if (engineer.getDepartment() != null) {
            responseDTO.setDepartmentId(engineer.getDepartment().getId());
            responseDTO.setDepartmentName(engineer.getDepartment().getName());
        }
        if (engineer.getManager() != null) {
            responseDTO.setManagerId(engineer.getManager().getId());
            responseDTO.setManagerName(engineer.getManager().getFirstName() + " " + engineer.getManager().getLastName());
        }

        return responseDTO;
    }
}
