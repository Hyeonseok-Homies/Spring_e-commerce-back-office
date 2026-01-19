package com.backoffice.review.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReviewDeleteResponseDto {
  private String customerName;
}
