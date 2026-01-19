package com.backoffice.order.entity;

import com.backoffice.admin.entity.Admin;
import com.backoffice.common.BaseEntity;
import com.backoffice.customer.entity.Customer;
import com.backoffice.product.entity.Product;
import jakarta.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@Table(name = "orders")
@SuperBuilder
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 30)
  private String reason;

  @Column(nullable = false)
  private String orderNo;

  @Column(nullable = false)
  private Long quantity;

  @Column(nullable = false)
  private Long price;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private OrderStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Created_By_Admin_Id") // nullable 옵션 제거
  private Admin admin;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Created_By_Customer_Id", nullable = false)
  private Customer customer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Created_By_Product_Id", nullable = false)
  private Product product;

  public Order(
      String orderNo, Long quantity, Long price, Admin admin, Customer customer, Product product) {
    this.orderNo = orderNo;
    this.quantity = quantity;
    this.price = price;
    this.status = OrderStatus.READY;
    this.admin = admin;
    this.customer = customer;
    this.product = product;
  }

  public static String generateOrderNumber() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String currentTime = dateFormat.format(new Date());
    String randomNumber = generateRandomNumber(6); // 주문번호의 랜덤한 숫자 부분 길이 (여기서는 6자리로 설정)

    return currentTime + randomNumber;
  }

  public static String generateRandomNumber(int length) {
    StringBuilder sb = new StringBuilder();
    Random random = new Random();

    for (int i = 0; i < length; i++) {
      int randomNumber = random.nextInt(10);
      sb.append(randomNumber);
    }

    return sb.toString();
  }

  public void changedStatus(OrderStatus newStatus) {
    if (this.status == null) {
      throw new IllegalStateException("상태를 입력해주세요.");
    } else if (this.status == OrderStatus.READY && newStatus != OrderStatus.SHIPPING) {
      throw new IllegalStateException("준비 상태에서는 배송중으로만 변경 가능합니다.");
    } else if (this.status == OrderStatus.SHIPPING && newStatus != OrderStatus.DELIVERED) {
      throw new IllegalStateException("배송중 상태에서는 배송완료 로만 변경 가능합니다.");
    } else if (this.status == OrderStatus.DELIVERED) {
      throw new IllegalStateException("이미 배송완료된 주문은 상태를 변경할 수 없습니다.");
    } else if (this.status == OrderStatus.CANCELED) {
      throw new IllegalStateException("취소된 주문은 상태를 변경할 수 없습니다.");
    }
    this.status = newStatus;
  }

  public void cancel(String reason) {
    if (this.status == OrderStatus.READY) {
      this.status = OrderStatus.CANCELED;
      this.reason = reason;
    } else {
      throw new IllegalStateException("배송준비 상태에서만 취소가 가능합니다.");
    }
  }
}
