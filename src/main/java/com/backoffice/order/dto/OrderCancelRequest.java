package com.backoffice.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class OrderCancelRequest {
    @NotBlank(message = "취소사유는 필수로 입력해주세요.")
    private String reason;
}
