package com.backoffice.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ReviewDetailsRequestDto {
  @NotNull(message = "상세보기할 리뷰가 입력되지 않았습니다.")
  private Long id;
}
