package com.backoffice.order.entity;

import com.backoffice.admin.entity.Admin;
import com.backoffice.customer.entity.Customer;
import com.backoffice.product.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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

  @Column(nullable = false, length = 30)
  private String orderNo;

  @Column(nullable = false)
  private Long quantity;

  @Column(nullable = false)
  private Long price;

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
  @JoinColumn(name = "Created_By_Product_id", nullable = false)
  private Product product;

  public Order(
      String orderNo,
      Long quantity,
      Long price,
      Long totalPrice,
      Admin admin,
      Customer customer,
      Product product) {
    this.orderNo = orderNo;
    this.quantity = quantity;
    this.price = price;
    this.totalPrice = totalPrice;
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
            int randomNumber = random.nextInt(8);
            sb.append(randomNumber);
        }

        return sb.toString();
    }
}
