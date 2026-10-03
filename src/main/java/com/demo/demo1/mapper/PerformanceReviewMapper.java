package com.demo.demo1.mapper;

import org.springframework.stereotype.Component;
import com.demo.demo1.dto.PerformanceReviewCreateDTO;
import com.demo.demo1.dto.PerformanceReviewResponseDTO;
import com.demo.demo1.dto.PerformanceReviewUpdateDTO;
import com.demo.demo1.entity.PerformanceReview;

@Component
public class PerformanceReviewMapper {

    public PerformanceReview toEntity(PerformanceReviewCreateDTO dto) {
        PerformanceReview review = new PerformanceReview();
        review.setReviewPeriod(dto.getReviewPeriod());
        review.setReviewDate(dto.getReviewDate());
        review.setRating(dto.getRating());
        review.setComments(dto.getComments());
        return review;
    }

    public void updateEntity(PerformanceReview review, PerformanceReviewUpdateDTO dto) {
        if (dto.getReviewPeriod() != null) review.setReviewPeriod(dto.getReviewPeriod());
        if (dto.getReviewDate() != null) review.setReviewDate(dto.getReviewDate());
        if (dto.getRating() != null) review.setRating(dto.getRating());
        if (dto.getComments() != null) review.setComments(dto.getComments());
    }

    public PerformanceReviewResponseDTO toResponseDTO(PerformanceReview review) {
        PerformanceReviewResponseDTO dto = new PerformanceReviewResponseDTO();
        dto.setId(review.getId());
        if (review.getEngineer() != null) {
            dto.setEngineerId(review.getEngineer().getId());
            dto.setEngineerName(review.getEngineer().getFirstName() + " " + review.getEngineer().getLastName());
        }
        if (review.getReviewer() != null) {
            dto.setReviewerId(review.getReviewer().getId());
            dto.setReviewerName(review.getReviewer().getFirstName() + " " + review.getReviewer().getLastName());
        }
        dto.setReviewPeriod(review.getReviewPeriod());
        dto.setReviewDate(review.getReviewDate());
        dto.setRating(review.getRating());
        dto.setComments(review.getComments());
        return dto;
    }
}
