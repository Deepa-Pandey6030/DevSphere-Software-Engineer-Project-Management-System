package com.demo.demo1.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.demo.demo1.dto.PerformanceReviewCreateDTO;
import com.demo.demo1.dto.PerformanceReviewResponseDTO;
import com.demo.demo1.dto.PerformanceReviewUpdateDTO;
import com.demo.demo1.entity.PerformanceReview;
import com.demo.demo1.exception.PerformanceReviewNotFoundException;
import com.demo.demo1.exception.SoftwareEngineerNotFoundException;
import com.demo.demo1.mapper.PerformanceReviewMapper;
import com.demo.demo1.repository.PerformanceReviewRepository;
import com.demo.demo1.repository.SoftwareEngineerRepository;

@Service
public class PerformanceReviewService {
    private final PerformanceReviewRepository reviewRepository;
    private final PerformanceReviewMapper reviewMapper;
    private final SoftwareEngineerRepository softwareEngineerRepository;

    public PerformanceReviewService(PerformanceReviewRepository reviewRepository,
                                    PerformanceReviewMapper reviewMapper,
                                    SoftwareEngineerRepository softwareEngineerRepository) {
        this.reviewRepository = reviewRepository;
        this.reviewMapper = reviewMapper;
        this.softwareEngineerRepository = softwareEngineerRepository;
    }

    public PerformanceReviewResponseDTO addReview(PerformanceReviewCreateDTO dto) {
        PerformanceReview review = reviewMapper.toEntity(dto);
        review.setEngineer(softwareEngineerRepository.findById(dto.getEngineerId())
            .orElseThrow(() -> new SoftwareEngineerNotFoundException("Software Engineer not found with id " + dto.getEngineerId())));
        review.setReviewer(softwareEngineerRepository.findById(dto.getReviewerId())
            .orElseThrow(() -> new SoftwareEngineerNotFoundException("Reviewer not found with id " + dto.getReviewerId())));

        PerformanceReview saved = reviewRepository.save(review);
        return reviewMapper.toResponseDTO(saved);
    }

    public List<PerformanceReviewResponseDTO> getAllReviews() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public PerformanceReviewResponseDTO getReviewById(Integer id) {
        PerformanceReview review = reviewRepository.findById(id)
                .orElseThrow(() -> new PerformanceReviewNotFoundException("Review not found with id " + id));
        return reviewMapper.toResponseDTO(review);
    }

    public PerformanceReviewResponseDTO updateReview(Integer id, PerformanceReviewUpdateDTO dto) {
        PerformanceReview existing = reviewRepository.findById(id)
                .orElseThrow(() -> new PerformanceReviewNotFoundException("Review not found with id " + id));
        reviewMapper.updateEntity(existing, dto);

        reviewRepository.save(existing);
        return reviewMapper.toResponseDTO(existing);
    }

    public void deleteReview(Integer id) {
        if (!reviewRepository.existsById(id)) {
            throw new PerformanceReviewNotFoundException("Review not found with id " + id);
        }
        reviewRepository.deleteById(id);
    }

    public List<PerformanceReviewResponseDTO> getReviewsByEngineer(Integer engineerId) {
        return reviewRepository.findByEngineerId(engineerId).stream()
                .map(reviewMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public List<PerformanceReviewResponseDTO> getReviewsByReviewer(Integer reviewerId) {
        return reviewRepository.findByReviewerId(reviewerId).stream()
                .map(reviewMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}