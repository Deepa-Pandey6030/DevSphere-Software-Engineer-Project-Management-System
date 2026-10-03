package com.demo.demo1.mapper;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.demo.demo1.dto.DepartmentCreateDTO;
import com.demo.demo1.dto.DepartmentResponseDTO;
import com.demo.demo1.dto.DepartmentUpdateDTO;
import com.demo.demo1.entity.Department;

@Component
public class DepartmentMapper {

    public Department toEntity(DepartmentCreateDTO dto) {
        Department department = new Department();
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        // Set deptCode here or in service layer
        department.setDeptCode("DEP-" + UUID.randomUUID());
        return department;
    }

    public void updateEntity(Department department, DepartmentUpdateDTO dto) {
        if (dto.getName() != null) department.setName(dto.getName());
        if (dto.getDescription() != null) department.setDescription(dto.getDescription());
    }

    public DepartmentResponseDTO toResponseDTO(Department department) {
        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setDeptCode(department.getDeptCode());
        dto.setDescription(department.getDescription());
        return dto;
    }
}