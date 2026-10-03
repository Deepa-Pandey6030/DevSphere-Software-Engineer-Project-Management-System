package com.demo.demo1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.demo.demo1.entity.SoftwareEngineer;



public interface SoftwareEngineerRepository extends JpaRepository<SoftwareEngineer,Integer>, JpaSpecificationExecutor<SoftwareEngineer>{
    Optional<SoftwareEngineer>findByEmail(String email);
    Optional<SoftwareEngineer>findByFirstName(String firstName);
    List<SoftwareEngineer> findByDepartmentId(Integer departmentId);
    List<SoftwareEngineer> findByManagerId(Integer managerId);
    List<SoftwareEngineer> findByDesignation(String designation);

    Page<SoftwareEngineer> findByDepartmentId(Integer departmentId, Pageable pageable);
    Page<SoftwareEngineer> findByManagerId(Integer managerId, Pageable pageable);
    Page<SoftwareEngineer> findByDesignation(String designation, Pageable pageable);
}