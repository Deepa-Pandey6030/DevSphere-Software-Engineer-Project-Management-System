package com.demo.demo1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.demo.demo1.dto.DepartmentCreateDTO;
import com.demo.demo1.dto.DepartmentResponseDTO;
import com.demo.demo1.dto.DepartmentUpdateDTO;
import com.demo.demo1.entity.Department;
import com.demo.demo1.exception.DepartmentNotFoundException;
import com.demo.demo1.mapper.DepartmentMapper;
import com.demo.demo1.repository.DepartmentRepository;

import jakarta.transaction.Transactional;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository, DepartmentMapper departmentMapper) {
        this.departmentRepository = departmentRepository;
        this.departmentMapper = departmentMapper;
    }

    public DepartmentResponseDTO addDept(DepartmentCreateDTO dto){
        Department department = departmentMapper.toEntity(dto);
        Department saved = departmentRepository.save(department);
        return departmentMapper.toResponseDTO(saved);
    }

    public List<DepartmentResponseDTO> getAllDepts(){
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public Page<DepartmentResponseDTO> getAllDeptsPaged(Pageable pageable){
        return departmentRepository.findAll(pageable)
                .map(departmentMapper::toResponseDTO);
    }

    public DepartmentResponseDTO getDeptByDeptCode(String deptCode){
        Department dept = departmentRepository.findByDeptCode(deptCode).orElseThrow(()->new DepartmentNotFoundException("Department Not found with "+deptCode));
        return departmentMapper.toResponseDTO(dept);
    }

    @Transactional
    public DepartmentResponseDTO updateDept(String deptCode, DepartmentUpdateDTO dto){
        Department existingDept = departmentRepository.findByDeptCode(deptCode)
                .orElseThrow(() -> new DepartmentNotFoundException("Department Not found with "+deptCode));

        departmentMapper.updateEntity(existingDept, dto);
        Department saved = departmentRepository.save(existingDept);
        return departmentMapper.toResponseDTO(saved);
    }

    @Transactional
    public void deleteDeptById(String deptCode){
        departmentRepository.deleteByDeptCode(deptCode).orElseThrow(()->new DepartmentNotFoundException("Department Not found with "+deptCode));
    }

}