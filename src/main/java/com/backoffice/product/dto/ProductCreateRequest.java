package com.backoffice.product.dto;

import com.backoffice.product.entity.ProductStatus;
import lombok.Getter;

@Getter
public class ProductCreateRequest {
  private String name;
  private String category;
  private Long price;
  private Long stock;
  private ProductStatus status;
}
