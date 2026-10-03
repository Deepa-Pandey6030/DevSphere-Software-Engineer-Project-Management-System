package com.demo.demo1.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.demo1.entity.SkillProficiency;

@Repository
public interface SkillProficiencyRepository extends JpaRepository<SkillProficiency, Integer> {
    List<SkillProficiency> findByEngineerId(Integer engineerId);
    List<SkillProficiency> findByTechnologyId(Integer technologyId);

    Page<SkillProficiency> findByEngineerId(Integer engineerId, Pageable pageable);
    Page<SkillProficiency> findByTechnologyId(Integer technologyId, Pageable pageable);
}