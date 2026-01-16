package com.backoffice.order.dto;

import com.backoffice.order.entity.OrderStatus;
import lombok.Getter;

@Getter
public class OrderUpdateResponse {
    private final Long id;
    private final String orderNo;
    private final OrderStatus orderStatus;

    public OrderUpdateResponse(Long id, String orderNo, OrderStatus orderStatus) {
        this.id = id;
        this.orderNo = orderNo;
        this.orderStatus = orderStatus;
    }
}
