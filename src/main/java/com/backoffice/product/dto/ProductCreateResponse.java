package com.backoffice.product.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductCreateResponse {

  private final Long id;
  private final String name;
  private final String category;
  private final Long price;
  private final Long stock;
  private final String status;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
  private final Long createdByAdminId;

  public ProductCreateResponse(
      Long id,
      String name,
      String category,
      Long price,
      Long stock,
      String status,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      Long createdByAdminId) {
    this.id = id;
    this.name = name;
    this.category = category;
    this.price = price;
    this.stock = stock;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.createdByAdminId = createdByAdminId;
  }
}
