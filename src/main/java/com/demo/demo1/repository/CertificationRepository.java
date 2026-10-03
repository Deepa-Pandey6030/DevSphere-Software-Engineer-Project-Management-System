package com.demo.demo1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.demo.demo1.entity.Certification;
import java.util.List;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, Integer> {
    List<Certification> findByEngineerId(Integer engineerId);
}
