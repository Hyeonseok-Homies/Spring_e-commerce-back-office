package com.backoffice.order.dto;

import com.backoffice.order.entity.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OrderUpdateRequest {
    @NotBlank(message = "상태를 입력해주세요.")
    private OrderStatus orderStatus;
}
