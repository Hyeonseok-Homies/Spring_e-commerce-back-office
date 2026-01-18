package com.backoffice.order.dto;

import com.backoffice.order.entity.Order;
import com.backoffice.order.entity.OrderStatus;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class OrderGetResponse {

    private final Long id;
    private final String orderNo;
    private final String customerName;
    private final String productName;
    private final Long quantity;
    private final Long totalPrice;
    private final LocalDateTime orderedAt;
    private final OrderStatus status;
    private final String adminName; // 관리자 주문이 아니면 null


    public OrderGetResponse (Order order) {
        this.id = order.getId();
        this.orderNo = order.getOrderNo();
        this.customerName = order.getCustomer().getName();
        this.productName = order.getProduct().getName();
        this.quantity = order.getQuantity();
        this.totalPrice = order.getTotalPrice();
        this.orderedAt = order.getOrderedAt();
        this.status = order.getStatus();
        this.adminName = (order.getAdmin() == null) ? null : order.getAdmin().getName();
    }
}
