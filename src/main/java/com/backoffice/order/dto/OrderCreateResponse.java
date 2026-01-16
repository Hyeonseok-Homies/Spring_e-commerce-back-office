package com.backoffice.order.dto;

import com.backoffice.product.entity.ProductStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderCreateResponse {
  private final Long id;
  private final String orderNo;
  private final String category;
  private final String name;
  private final Long quantity;
  private final Long price;
  private final Long totalPrice;
  private final Long stock;
  private final ProductStatus status;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
  private final Long customerId;
  private final String adminName;

  public OrderCreateResponse(
      Long id,
      String orderNo,
      String category,
      String name,
      Long quantity,
      Long price,
      Long totalPrice,
      Long stock,
      ProductStatus status,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      Long customerId,
      String adminName) {
    this.id = id;
    this.orderNo = orderNo;
    this.category = category;
    this.name = name;
    this.quantity = quantity;
    this.price = price;
    this.totalPrice = totalPrice;
    this.stock = stock;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.customerId = customerId;
    this.adminName = adminName;
  }
}
