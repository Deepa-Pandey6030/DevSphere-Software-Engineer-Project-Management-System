package com.demo.demo1.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.demo1.dto.PerformanceReviewCreateDTO;
import com.demo.demo1.dto.PerformanceReviewResponseDTO;
import com.demo.demo1.dto.PerformanceReviewUpdateDTO;
import com.demo.demo1.service.PerformanceReviewService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/performance-reviews")
public class PerformanceReviewController {
    private final PerformanceReviewService reviewService;

    public PerformanceReviewController(PerformanceReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/insert")
    public ResponseEntity<PerformanceReviewResponseDTO> addReview(@Valid @RequestBody PerformanceReviewCreateDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.addReview(request));
    }

    @GetMapping("/")
    public ResponseEntity<List<PerformanceReviewResponseDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PerformanceReviewResponseDTO> getReviewById(@PathVariable Integer id) {
        return ResponseEntity.ok(reviewService.getReviewById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PerformanceReviewResponseDTO> updateReview(@PathVariable Integer id, @Valid @RequestBody PerformanceReviewUpdateDTO request) {
        return ResponseEntity.ok(reviewService.updateReview(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Integer id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/engineer/{engineerId}")
    public ResponseEntity<List<PerformanceReviewResponseDTO>> getReviewsByEngineer(@PathVariable Integer engineerId) {
        return ResponseEntity.ok(reviewService.getReviewsByEngineer(engineerId));
    }

    @GetMapping("/reviewer/{reviewerId}")
    public ResponseEntity<List<PerformanceReviewResponseDTO>> getReviewsByReviewer(@PathVariable Integer reviewerId) {
        return ResponseEntity.ok(reviewService.getReviewsByReviewer(reviewerId));
    }
}
