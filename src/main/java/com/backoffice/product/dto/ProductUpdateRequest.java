package com.backoffice.product.dto;

import com.backoffice.product.entity.ProductStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProductUpdateRequest {

  @NotBlank(message = "상품명은 필수 입력 사항입니다.")
  private String name;

  @NotBlank(message = "카테고리는 필수 입력 사항입니다.")
  private String category;

  @NotNull(message = "가격은 필수 입력 사항입니다.")
  private Long price;

  @NotNull(message = "재고는 필수 입력 사항입니다.")
  private Long stock;

  @NotNull(message = "상품 상태는 필수 입력 사항입니다.")
  private ProductStatus status;
}
