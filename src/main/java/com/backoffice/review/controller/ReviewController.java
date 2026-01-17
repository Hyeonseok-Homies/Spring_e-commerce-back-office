package com.backoffice.review.controller;

import com.backoffice.admin.config.Login;
import com.backoffice.admin.dto.SessionAdmin;
import com.backoffice.review.dto.ReviewDeleteResponseDto;
import com.backoffice.review.dto.ReviewDetailsResponseDto;
import com.backoffice.review.dto.ReviewResponseDto;
import com.backoffice.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<ReviewResponseDto> getReviewList(
            @Login SessionAdmin admin,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer grade,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction
    ) {
        ReviewResponseDto response = reviewService.getReviewAll(admin, keyword, grade, page, size, sortBy, direction);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDetailsResponseDto> getReviewDetail(
            @Login SessionAdmin admin,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(reviewService.getReviewDetail(admin, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ReviewDeleteResponseDto> deleteReview(
            @Login SessionAdmin admin,
            @PathVariable Long id
    ) {
        ReviewDeleteResponseDto response = reviewService.deleteReview(admin, id);
        return ResponseEntity.ok(response);
    }
}
