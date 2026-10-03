package com.demo.demo1.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.demo1.entity.Department;

public interface DepartmentRepository extends  JpaRepository<Department, Integer> {
    Optional<Department>findByDeptCode(String deptCode);
    Optional<Department>deleteByDeptCode(String deptCode);
}
