package com.backoffice.review.dto;

import com.backoffice.review.entity.Review;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReviewDetailsResponseDto {
  private String productName;
  private String customerName;
  private String customerEmail;
  private LocalDateTime createdAt;
  private int grade;
  private String reviewContent;

  public ReviewDetailsResponseDto(Review review) {
    this.productName = review.getProduct().getName();
    this.customerName = review.getCustomer().getName();
    this.customerEmail = review.getCustomer().getEmail();
    this.createdAt = review.getCreatedAt();
    this.grade = review.getGrade();
    this.reviewContent = review.getReviewContent();
  }
}
