package com.backoffice.order.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {
  READY("준비중"),
  SHIPPING("배송중"),
  DELIVERED("배송완료"),
  CANCELED("취소됨");

  private final String value;
}
