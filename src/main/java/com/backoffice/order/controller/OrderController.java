package com.backoffice.order.controller;

import com.backoffice.admin.config.Login;
import com.backoffice.admin.dto.SessionAdmin;
import com.backoffice.order.dto.*;
import com.backoffice.order.entity.OrderStatus;
import com.backoffice.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderCreateResponse> create( // 오타 수정
      @Login SessionAdmin sessionAdmin, @Valid @RequestBody OrderCreateRequest request) {
    Long adminId = sessionAdmin.getId();
    return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(request, adminId));
  }

  @PatchMapping("/{orderId}/status") // ordersId -> orderId로 통일
  public ResponseEntity<OrderUpdateResponse> updateStatus(
      @Login SessionAdmin sessionAdmin,
      @PathVariable Long orderId, // ordersId -> orderId로 통일
      @Valid @RequestBody OrderUpdateRequest request) {
    if (sessionAdmin == null) {
      throw new IllegalStateException("저장된 정보가 없습니다.");
    }
    return ResponseEntity.status(HttpStatus.OK)
        .body(orderService.update(orderId, request)); // ordersId -> orderId로 통일
  }

  @GetMapping
  public ResponseEntity<OrderListResponse<OrderGetResponse>> getAll(
      @Login SessionAdmin sessionAdmin,
      @RequestParam(required = false) String keyword,
      @RequestParam(required = false) OrderStatus status,
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer size,
      @RequestParam(defaultValue = "cratedAt") String sortBy,
      @RequestParam(defaultValue = "desc") String sortOrder) {
    return ResponseEntity.ok(orderService.getAll(keyword, status, page, size, sortBy, sortOrder));
  }

  @GetMapping("/{orderId}")
  public ResponseEntity<OrderDetailResponse> getOne(
      @Login SessionAdmin sessionAdmin, @PathVariable Long orderId) {
    return ResponseEntity.ok(orderService.getOne(orderId));
  }

  @PostMapping("/{orderId}/cancel")
  public ResponseEntity<OrderCancelResponse> cancel(
      @Login SessionAdmin sessionAdmin,
      @PathVariable Long orderId,
      @Valid @RequestBody OrderCancelRequest request) {
    if (sessionAdmin == null) {
      throw new IllegalStateException("저장된 정보가 없습니다.");
    }
    return ResponseEntity.status(HttpStatus.OK).body(orderService.orderCancel(orderId, request));
  }
}
