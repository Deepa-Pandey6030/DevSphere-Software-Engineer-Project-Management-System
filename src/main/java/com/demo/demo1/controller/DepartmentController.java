package com.demo.demo1.controller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.demo1.dto.DepartmentCreateDTO;
import com.demo.demo1.dto.DepartmentResponseDTO;
import com.demo.demo1.dto.DepartmentUpdateDTO;
import com.demo.demo1.service.DepartmentService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {
    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;

    }

    @PostMapping("/insert")
    public DepartmentResponseDTO addDepartment(@Valid @RequestBody DepartmentCreateDTO department) {
        return departmentService.addDept(department);
    }

    @GetMapping("/")
    public Page<DepartmentResponseDTO> getAllDepartments(@PageableDefault(size = 20, sort = "id") Pageable pageable){
        return departmentService.getAllDeptsPaged(pageable);
    }

    @GetMapping("/{deptCode}")
    public DepartmentResponseDTO getDepartmentByDeptCode(@PathVariable String deptCode){
        return departmentService.getDeptByDeptCode(deptCode);
    }

    @PatchMapping("/{deptCode}")
    public DepartmentResponseDTO updateDepartment(@PathVariable String deptCode, @Valid @RequestBody DepartmentUpdateDTO request){
        return departmentService.updateDept(deptCode, request);
    }

    @DeleteMapping("/{deptCode}")
    public void deleteDepartmentById(@PathVariable String deptCode){
        departmentService.deleteDeptById(deptCode);
    }

}