package com.backoffice.order.dto;

import com.backoffice.order.entity.Order;
import com.backoffice.order.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderDetailResponse {

  private final String orderNo;
  private final String customerName;
  private final String customerEmail;
  private final String productName;
  private final Long quantity;
  private final Long price;
  private final LocalDateTime orderedAt;
  private final OrderStatus status;
  private final String adminName; // 관리자 주문이 아니면 null
  private final String adminEmail; // 관리자 주문이 아니면 null
  private final String adminRole; // 관리자 주문이 아니면 null

  public OrderDetailResponse(Order order) {
    this.orderNo = order.getOrderNo();
    this.customerName = order.getCustomer().getName();
    this.customerEmail = order.getCustomer().getEmail();
    this.productName = order.getProduct().getName();
    this.quantity = order.getQuantity();
    this.price = order.getPrice();
    this.orderedAt = order.getCreatedAt();
    this.status = order.getStatus();

    if (order.getAdmin() == null) {
      this.adminName = null;
      this.adminEmail = null;
      this.adminRole = null;
    } else {
      this.adminName = order.getAdmin().getName();
      this.adminEmail = order.getAdmin().getEmail();
      this.adminRole = order.getAdmin().getRole().name();
    }
  }
}
