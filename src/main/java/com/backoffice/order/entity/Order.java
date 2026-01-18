package com.backoffice.order.entity;

import com.backoffice.admin.entity.Admin;
import com.backoffice.common.BaseEntity;
import com.backoffice.customer.entity.Customer;
import com.backoffice.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 30)
  private String orderNo;

  @Column(nullable = false)
  private Long quantity;

  @Column(nullable = false)
  private Long unitPrice;

  @Column(nullable = false)
  private Long totalPrice;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private OrderStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Created_By_Admin_Id", nullable = false)
  private Admin admin;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Created_By_Customer_Id", nullable = false)
  private Customer customer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Created_By_Product_Id", nullable = false)
  private Product product;

  public Order(
      String orderNo,
      Long quantity,
      Long unitPrice,
      Long totalPrice,
      OrderStatus status,
      Admin admin,
      Customer customer,
      Product product) {
    this.orderNo = orderNo;
    this.quantity = quantity;
    this.unitPrice = unitPrice;
    this.totalPrice = totalPrice;
    this.status = status;
    this.admin = admin;
    this.customer = customer;
    this.product = product;
  }
}
