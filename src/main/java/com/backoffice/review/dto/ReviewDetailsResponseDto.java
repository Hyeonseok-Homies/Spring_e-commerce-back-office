package com.backoffice.review.dto;

import lombok.Builder;
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

  @Builder
  public ReviewDetailsResponseDto(
      String productName,
      String customerName,
      String customerEmail,
      LocalDateTime createdAt,
      int grade,
      String reviewContent) {
    this.productName = productName;
    this.customerName = customerName;
    this.customerEmail = customerEmail;
    this.createdAt = createdAt;
    this.grade = grade;
    this.reviewContent = reviewContent;
  }
}
