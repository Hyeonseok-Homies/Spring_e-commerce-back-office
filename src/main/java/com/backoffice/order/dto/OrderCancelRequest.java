package com.backoffice.order.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderCancelRequest {
  @NotNull(message = "취소사유는 필수로 입력해주세요.")
  private String reason;
}
