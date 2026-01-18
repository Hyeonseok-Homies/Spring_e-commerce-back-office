package com.backoffice.order.repository;

import com.backoffice.order.entity.Order;
import com.backoffice.order.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // 리스트 조회 : keyword(주문번호/고객명) + status 필터
    @Query("""
        select o
        from Order o
        join o.customer c
        join o.admin a
        where (:status is null or o.status = :status)
          and (
                :keyword is null
                or :keyword = ''
                or lower(o.orderNo) like lower(concat('%', :keyword, '%'))
                or lower(c.name) like lower(concat('%', :keyword, '%'))
          )
    """)
    Page<Order> search(
            @Param("keyword") String keyword,
            @Param("adminName") String adminName,
            @Param("status") OrderStatus status,
            Pageable pageable
    );

    @Query("""
        select o
        from Order o
        join fetch o.customer c
        join fetch o.product p
        join fetch o.admin a
        where o.id = :id
    """)
    Optional<Order> findDetailById(@Param("id") Long id);
}