package com.backoffice.order.service;


import com.backoffice.order.dto.OrderDetailResponse;
import com.backoffice.order.dto.OrderGetResponse;
import com.backoffice.order.dto.OrderListResponse;
import com.backoffice.order.entity.Order;
import com.backoffice.order.entity.OrderStatus;
import com.backoffice.order.repository.OrderRepository;
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

  @Transactional(readOnly = true)
  public OrderListResponse<OrderGetResponse> getAll(
          String keyword,
          OrderStatus status,
          Integer page,
          Integer size,
          String sortBy,     // quantity / totalPrice / orderedAt
          String sortOrder   // asc / desc
  ) {
      int pageIndex = Math.max((page == null ? 1 : page) - 1, 0);
      int pageSize = (size == null || size < 1) ? 10 : size;

      Sort.Direction direction =
              "asc".equalsIgnoreCase(sortOrder) ? Sort.Direction.ASC : Sort.Direction.DESC;

      // ✅ 요구사항 정렬 기준: 수량, 금액, 주문일
      String sortField = switch (sortBy) {
          case "quantity" -> "quantity";
          case "totalPrice" -> "totalPrice";
          default -> "orderedAt"; // 주문일(= BaseEntity의 @CreatedDate)
      };

      Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(direction, sortField));

      Page<OrderGetResponse> result = orderRepository.search(keyword, status, pageable)
              .map(OrderGetResponse::new);

      return new OrderListResponse<>(result);
  }

    public OrderDetailResponse getOne(Long id) {
        Order order = orderRepository.findDetailById(id)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다. id=" + id));

        return new OrderDetailResponse(order);
    }
}