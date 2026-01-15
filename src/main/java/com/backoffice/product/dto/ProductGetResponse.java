package com.backoffice.product.dto;

import com.backoffice.product.entity.Product;
import com.backoffice.product.entity.ProductStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProductGetResponse {
  private final Long id; // 추가됨
  private final String name;
  private final String category;
  private final Long price;
  private final Long stock;
  private final ProductStatus status;
  private final LocalDateTime createdAt;
  private final String adminName;
  private final String adminEmail;

  public ProductGetResponse(Product product) {
    this.id = product.getId();
    this.name = product.getName();
    this.category = product.getCategory();
    this.price = product.getPrice();
    this.stock = product.getStock();
    this.status = product.getStatus();
    this.createdAt = product.getCreatedAt();
    this.adminName = product.getAdmin().getName();
    this.adminEmail = product.getAdmin().getEmail();
  }
}
