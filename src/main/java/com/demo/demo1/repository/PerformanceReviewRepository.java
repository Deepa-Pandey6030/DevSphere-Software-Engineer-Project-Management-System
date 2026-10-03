package com.demo.demo1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.demo.demo1.entity.PerformanceReview;
import java.util.List;

@Repository
public interface PerformanceReviewRepository extends JpaRepository<PerformanceReview, Integer> {
    List<PerformanceReview> findByEngineerId(Integer engineerId);
    List<PerformanceReview> findByReviewerId(Integer reviewerId);
}
