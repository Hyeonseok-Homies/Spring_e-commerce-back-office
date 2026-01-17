package com.backoffice.order.dto;

import com.backoffice.order.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OrderUpdateRequest {
    @NotNull(message = "상태를 입력해주세요.")
    private OrderStatus status;
}
