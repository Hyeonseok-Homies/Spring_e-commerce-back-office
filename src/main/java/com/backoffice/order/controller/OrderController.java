package com.backoffice.order.controller;

import com.backoffice.admin.config.Login;
import com.backoffice.admin.dto.SessionAdmin;
import com.backoffice.order.dto.OrderDetailResponse;
import com.backoffice.order.dto.OrderGetResponse;
import com.backoffice.order.dto.OrderListResponse;
import com.backoffice.order.entity.OrderStatus;
import com.backoffice.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/api/orders")
    public ResponseEntity<OrderListResponse<OrderGetResponse>> getAll(
            @Login SessionAdmin sessionAdmin,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "orderedAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortOrder
    ) {
        return ResponseEntity.ok(
                orderService.getAll(keyword, status, page, size, sortBy, sortOrder)
        );
    }

    @GetMapping("/api/orders/{id}")
    public ResponseEntity<OrderDetailResponse> getOne(
            @Login SessionAdmin sessionAdmin,
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(orderService.getOne(id));
    }
}
