package com.backoffice.order.controller;

import com.backoffice.admin.config.Login;
import com.backoffice.admin.dto.SessionAdmin;
import com.backoffice.order.dto.*;
import com.backoffice.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
  private final OrderService orderService;

  @PostMapping
  public ResponseEntity<OrderCreateResponse> creat(
      @Login SessionAdmin sessionAdmin, @Valid @RequestBody OrderCreateRequest request) {
    Long adminId = sessionAdmin.getId();
    return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(request, adminId));
  }

  @PatchMapping("/{ordersId}/status")
  public ResponseEntity<OrderUpdateResponse> updateStatus(
      @Login SessionAdmin sessionAdmin,
      @PathVariable  Long ordersId,
      @Valid @RequestBody OrderUpdateRequest request) {
      if(sessionAdmin == null)
      {
          throw new IllegalStateException("저장된 정보가 없습니다.");
      }
      return ResponseEntity.status(HttpStatus.OK).body(orderService.update(ordersId, request));
  }

  @PostMapping("/{orderId}/cancel")
    public ResponseEntity<OrderCancelResponse> cancel(
            @Login SessionAdmin sessionAdmin,
            @PathVariable Long orderId,
            @Valid @RequestBody OrderCancelRequest request) {
      if(sessionAdmin == null)
      {
          throw new IllegalStateException("저장된 정보가 없습니다.");
      }
      return ResponseEntity.status(HttpStatus.OK).body(orderService.orderCancel(orderId, request));
  }
}
