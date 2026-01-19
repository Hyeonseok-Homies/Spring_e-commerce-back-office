package com.backoffice.order.service;

import static com.backoffice.order.entity.Order.generateOrderNumber;

import com.backoffice.order.dto.OrderDetailResponse;
import com.backoffice.order.dto.OrderGetResponse;
import com.backoffice.order.dto.OrderListResponse;
import com.backoffice.admin.entity.Admin;
import com.backoffice.admin.repository.AdminRepository;
import com.backoffice.customer.entity.Customer;
import com.backoffice.customer.repository.CustomerRepository;
import com.backoffice.order.dto.*;
import com.backoffice.order.entity.Order;
import com.backoffice.order.entity.OrderStatus;
import com.backoffice.order.repository.OrderRepository;
import com.backoffice.product.entity.Product;
import com.backoffice.product.entity.ProductStatus;
import com.backoffice.product.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {
  private final OrderRepository orderRepository;
  private final AdminRepository adminRepository;
  private final ProductRepository productRepository;
  private final CustomerRepository customerRepository;

  @Transactional
  public OrderCreateResponse save(OrderCreateRequest request, Long adminId) {
    Admin admin =
        adminRepository
            .findById(adminId)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 어드민입니다"));
    Customer customer =
        customerRepository
            .findById(request.getCreatedByCustomerId())
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 고객입니다."));
    Product product =
        productRepository
            .findById((request.getCreatedByProductId()))
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 상품입니다."));

    if (product.getStock() < request.getQuantity()) {
      throw new IllegalStateException("재고가 부족합니다. (남은 재고: " + product.getStock() + ")");
    }
    if (product.getStatus() == ProductStatus.DISCONTINUED) {
      throw new IllegalStateException("상품이 단종 상태입니다.");
    }
    if (product.getStatus() == ProductStatus.SOLDOUT) {
      throw new IllegalStateException("상품이 품절 상태입니다.");
    }
    // 가격 계산
    Long totalPrice = product.getPrice() * request.getQuantity();

    // 재고 차감
    product.removeStock(request.getQuantity());
    Product savedproduct = productRepository.save(product);

    // 주문번호 생성
    String orderNumber = generateOrderNumber();

    Order order =
        new Order(orderNumber, request.getQuantity(), totalPrice, admin, customer, product);
    Order savedOrder = orderRepository.save(order);

    return new OrderCreateResponse(
        savedOrder.getId(),
        orderNumber,
        savedproduct.getCategory(),
        savedproduct.getName(),
        savedOrder.getQuantity(),
        totalPrice,
        savedOrder.getStatus(),
        savedOrder.getCreatedAt(),
        savedOrder.getUpdatedAt(),
        customer.getName(),
        admin.getName());
  }

  @Transactional(readOnly = true)
  public OrderListResponse<OrderGetResponse> getAll(
      String keyword,
      OrderStatus status,
      Integer page,
      Integer size,
      String sortBy, // quantity / totalPrice / orderedAt
      String sortOrder // asc / desc
      ) {
    int pageIndex = Math.max((page == null ? 1 : page) - 1, 0);
    int pageSize = (size == null || size < 1) ? 10 : size;

    Sort.Direction direction =
        "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;

    // 요구사항 정렬 기준: 수량, 금액, 주문일
    String sortField =
        switch (sortBy) {
          case "quantity" -> "quantity";
          case "totalPrice" -> "totalPrice";
          default -> "createdAt"; // 주문일(= BaseEntity의 @CreatedDate)
        };

    Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(direction, sortField));

    Page<OrderGetResponse> result =
        orderRepository.search(keyword, status, pageable).map(OrderGetResponse::new);

    return new OrderListResponse<>(result);
  }

  public OrderDetailResponse getOne(Long id) {
    Order order =
        orderRepository
            .findDetailById(id)
            .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다. id=" + id));

    return new OrderDetailResponse(order);
  }

  @Transactional
  public OrderUpdateResponse update(Long ordersId, OrderUpdateRequest request) {
    Order order =
        orderRepository
            .findById(ordersId)
            .orElseThrow(() -> new IllegalStateException("없는 주문입니다."));

    // 업데이트
    order.changedStatus(request.getStatus());

    return new OrderUpdateResponse(order.getId(), order.getOrderNo(), order.getStatus());
  }

  @Transactional
  public OrderCancelResponse orderCancel(Long orderId, @Valid OrderCancelRequest request) {
    Order order =
        orderRepository.findById(orderId).orElseThrow(() -> new IllegalStateException("없는 주문입니다."));

    if (request.getReason() == null) {
      throw new IllegalStateException("취소사유는 필수로 입력해주세요.");
    }

    // 주문 취소
    order.cancel(request.getReason());
    Product product = order.getProduct();
    product.addStock(order.getQuantity());

    Order savedOrder = orderRepository.save(order);

    return new OrderCancelResponse(
        savedOrder.getId(),
        savedOrder.getOrderNo(),
        savedOrder.getStatus(),
        savedOrder.getReason());
  }
}
