package com.backoffice.order.dto;

import com.backoffice.order.entity.OrderStatus;
import com.backoffice.product.entity.ProductStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderCreateResponse {
  private final Long id;
  private final String orderNo;
  private final String category;
  private final String productName;
  private final Long quantity;
  private final Long price;
  private final OrderStatus status;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;
  private final String customerName;
  private final String adminName;

  public OrderCreateResponse(
      Long id,
      String orderNo,
      String category,
      String productName,
      Long quantity,
      Long price,
      OrderStatus status,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      String customerName,
      String adminName) {
    this.id = id;
    this.orderNo = orderNo;
    this.category = category;
    this.productName = productName;
    this.quantity = quantity;
    this.price = price;
    this.status = status;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.customerName = customerName;
    this.adminName = adminName;
  }
}
