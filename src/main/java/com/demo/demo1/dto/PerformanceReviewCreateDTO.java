package com.demo.demo1.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PerformanceReviewCreateDTO {
    @NotNull(message="Engineer ID is required")
    @Positive(message="Engineer ID must be positive")
    private Integer engineerId;
    
    @NotNull(message="Reviewer ID is required")
    @Positive(message="Reviewer ID must be positive")
    private Integer reviewerId;
    
    @NotNull(message="Review period is required")
    private String reviewPeriod;
    
    @NotNull(message="Review date is required")
    private LocalDate reviewDate;
    
    @NotNull(message="Rating is required")
    private String rating;
    
    private String comments;

    public Integer getEngineerId() {
        return engineerId;
    }

    public void setEngineerId(Integer engineerId) {
        this.engineerId = engineerId;
    }

    public Integer getReviewerId() {
        return reviewerId;
    }

    public void setReviewerId(Integer reviewerId) {
        this.reviewerId = reviewerId;
    }

    public String getReviewPeriod() {
        return reviewPeriod;
    }

    public void setReviewPeriod(String reviewPeriod) {
        this.reviewPeriod = reviewPeriod;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
