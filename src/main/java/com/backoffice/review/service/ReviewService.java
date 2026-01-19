package com.backoffice.review.service;

import com.backoffice.admin.dto.SessionAdmin;
import com.backoffice.review.dto.*;
import com.backoffice.review.entity.Review;
import com.backoffice.review.repository.ReviewRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

  private final ReviewRepository reviewRepository;

  public ReviewResponseDto getReviewAll(
      SessionAdmin admin,
      String keyword,
      Integer grade,
      int page,
      int size,
      String sortBy,
      String direction) {
    validateAdmin(admin);

    Sort sort =
        direction.equalsIgnoreCase("asc")
            ? Sort.by(sortBy).ascending()
            : Sort.by(sortBy).descending();
    Pageable pageable = PageRequest.of(page - 1, size, sort);

    Page<Review> reviewPage = reviewRepository.searchReviews(keyword, grade, pageable);

    List<ReviewResponseDto.ReviewListElementDto> dtoList =
        reviewPage.getContent().stream()
            .map(
                review ->
                    ReviewResponseDto.ReviewListElementDto.builder()
                        .id(review.getId())
                        .orderNumber(review.getOrderNumber())
                        .customerName(review.getCustomerName())
                        .productName(review.getProductName())
                        .grade(review.getGrade())
                        .reviewContent(review.getReviewContent())
                        .createdAt(review.getCreatedAt())
                        .build())
            .collect(Collectors.toList());

    return new ReviewResponseDto(dtoList, reviewPage);
  }

  public ReviewDetailsResponseDto getReviewDetail(SessionAdmin admin, Long id) {
    validateAdmin(admin);

    Review review =
        reviewRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰 ID입니다: " + id));

    return ReviewDetailsResponseDto.builder()
        .productName(review.getProductName())
        .customerName(review.getCustomerName())
        .customerEmail(review.getCustomerEmail())
        .createdAt(review.getCreatedAt())
        .grade(review.getGrade())
        .reviewContent(review.getReviewContent())
        .build();
  }

  @Transactional
  public ReviewDeleteResponseDto deleteReview(SessionAdmin admin, Long id) {
    validateAdmin(admin);

    Review review =
        reviewRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 리뷰입니다."));

    review.delete(admin.getEmail());

    reviewRepository.delete(review);

    return ReviewDeleteResponseDto.builder().customerName(review.getCustomerName()).build();
  }

  private void validateAdmin(SessionAdmin admin) {
    if (admin == null || admin.getEmail() == null) {
      throw new IllegalStateException("유효하지 않은 관리자 세션입니다.");
    }
  }
}
