package com.backoffice.order.dto;

import com.backoffice.order.entity.OrderStatus;
import lombok.Getter;

@Getter
public class OrderCancelResponse {
    private final Long id;
    private final String orderNo;
    private final OrderStatus status;
    private final String reason;

    public OrderCancelResponse(Long id, String orderNo, OrderStatus status, String reason) {
        this.id = id;
        this.orderNo = orderNo;
        this.status = status;
        this.reason = reason;
    }
}
