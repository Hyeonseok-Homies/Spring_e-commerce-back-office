package com.backoffice.order.controller;

import com.backoffice.admin.config.Login;
import com.backoffice.admin.dto.SessionAdmin;
import com.backoffice.order.dto.OrderCreateRequest;
import com.backoffice.order.dto.OrderCreateResponse;
import com.backoffice.order.dto.OrderUpdateRequest;
import com.backoffice.order.dto.OrderUpdateResponse;
import com.backoffice.order.service.OrderService;
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
      @Login SessionAdmin sessionAdmin, @RequestBody OrderCreateRequest request) {
    Long adminId = sessionAdmin.getId();
    return ResponseEntity.status(HttpStatus.CREATED).body(orderService.save(request, adminId));
  }

  @PatchMapping("/{ordersId}/status")
  public ResponseEntity<OrderUpdateResponse> updateStatus(
      @Login SessionAdmin sessionAdmin,
      @PathVariable  Long ordersId,
      @RequestBody OrderUpdateRequest request) {
      return ResponseEntity.status(HttpStatus.OK).body(orderService.update(ordersId, request));
  }
}
