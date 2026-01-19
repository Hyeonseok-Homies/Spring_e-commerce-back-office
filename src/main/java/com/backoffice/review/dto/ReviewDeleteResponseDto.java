package com.backoffice.review.dto;

import com.backoffice.review.entity.Review;
import lombok.Getter;

@Getter
public class ReviewDeleteResponseDto {
  private String customerName;

  public ReviewDeleteResponseDto(Review review) {

    this.customerName = review.getCustomer().getName();
  }
}
