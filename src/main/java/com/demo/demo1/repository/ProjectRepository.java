package com.demo.demo1.repository;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.demo.demo1.entity.Project;
import com.demo.demo1.entity.ProjectStatus;
public interface ProjectRepository extends JpaRepository<Project,Integer>, JpaSpecificationExecutor<Project>{
    List<Project> findByStatus(ProjectStatus status);
    List<Project> findByStartDateBetween(LocalDate startDate, LocalDate endDate);

    Page<Project> findByStatus(ProjectStatus status, Pageable pageable);
    Page<Project> findByStartDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);
}