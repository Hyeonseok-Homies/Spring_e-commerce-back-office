package com.backoffice.product.dto;

import com.backoffice.product.entity.ProductStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductGetResponse {

  private final String name;
  private final String category;
  private final Long price;
  private final Long stock;
  private final ProductStatus status;
  private final LocalDateTime createdAt;
  private final Long createdByAdminId;

  public ProductGetResponse(
      String name,
      String category,
      Long price,
      Long stock,
      ProductStatus status,
      LocalDateTime createdAt,
      Long createdByAdminId) {
    this.name = name;
    this.category = category;
    this.price = price;
    this.stock = stock;
    this.status = status;
    this.createdAt = createdAt;
    this.createdByAdminId = createdByAdminId;
  }
}
