package com.backoffice.order.service;

import com.backoffice.admin.entity.Admin;
import com.backoffice.admin.repository.AdminRepository;
import com.backoffice.customer.entity.Customer;
import com.backoffice.customer.repository.CustomerRepository;
import com.backoffice.order.dto.OrderCreateRequest;
import com.backoffice.order.dto.OrderCreateResponse;
import com.backoffice.order.dto.OrderUpdateRequest;
import com.backoffice.order.dto.OrderUpdateResponse;
import com.backoffice.order.entity.Order;
import com.backoffice.order.entity.OrderStatus;
import com.backoffice.order.repository.OrderRepository;
import com.backoffice.product.entity.Product;
import com.backoffice.product.entity.ProductStatus;
import com.backoffice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.backoffice.order.entity.Order.generateOrderNumber;

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
    // product.removeStock(request.getQuantity());
    Product savedproduct = productRepository.save(product);

    // 주문번호 생성
    String orderNumber = generateOrderNumber();

    Order order =
        new Order(
            orderNumber,
            request.getQuantity(),
            savedproduct.getPrice(),
            totalPrice,
            admin,
            customer,
            product);
    Order savedOrder = orderRepository.save(order);
    return new OrderCreateResponse(
        savedOrder.getId(),
        orderNumber,
        savedproduct.getCategory(),
        savedproduct.getName(),
        savedOrder.getQuantity(),
        savedproduct.getPrice(),
        totalPrice,
        savedproduct.getStock(),
        savedproduct.getStatus(),
        savedOrder.getCreatedAt(),
        savedOrder.getUpdatedAt(),
        customer.getId(),
        admin.getName());
  }

  @Transactional
  public OrderUpdateResponse update(Long ordersId, OrderUpdateRequest request) {
    Order order =
        orderRepository
            .findById(ordersId)
            .orElseThrow(() -> new IllegalStateException("없는 주문입니다."));
    if (order.getStatus() == null) {
      throw new IllegalStateException("상태를 입력해주세요.");
    }


    return null;
  }
}
