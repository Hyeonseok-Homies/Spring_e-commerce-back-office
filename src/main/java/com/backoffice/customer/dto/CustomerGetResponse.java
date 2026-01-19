package com.backoffice.customer.dto;

import com.backoffice.customer.entity.CustomerStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CustomerGetResponse {

  private final Long id;
  private final String name;
  private final String email;
  private final CustomerStatus status; // enum 변경으로 인한 변경
  private final LocalDateTime createdDate;
  private final Long totalOrders; // 총 주문 수
  private final Long totalPurchaseAmount; // 총 구매 금액

  public CustomerGetResponse(
      Long id,
      String name,
      String email,
      CustomerStatus status,
      LocalDateTime createdDate,
      Long totalOrders,
      Long totalPurchaseAmount
  ) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.status = status;
    this.createdDate = createdDate;
    this.totalOrders = totalOrders;
    this.totalPurchaseAmount = totalPurchaseAmount;
  }
}
