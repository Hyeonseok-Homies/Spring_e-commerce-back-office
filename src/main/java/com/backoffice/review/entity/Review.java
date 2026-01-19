package com.backoffice.review.entity;

import com.backoffice.common.BaseEntity;
import com.backoffice.customer.entity.Customer;
import com.backoffice.order.entity.Order;
import com.backoffice.product.entity.Product;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "review")
@SQLDelete(sql = "UPDATE review SET deleted_at = NOW() WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Review extends BaseEntity { // admin.entity.BaseEntity

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Min(1)
  @Max(5)
  @Column(nullable = false, length = 50)
  private int grade;

  @NotBlank
  @Column(nullable = false, length = 500)
  private String reviewContent;

  private LocalDateTime deletedAt;
  private String deletedBy;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "By_Order_No")
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Created_By_Customer_Id")
  private Customer customer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Created_By_Product_Id")
  private Product product;

  public Review(
      Long id, int grade, String reviewContent, Customer customer, Product product, Order order) {
    this.id = id;
    this.grade = grade;
    this.reviewContent = reviewContent;
    this.customer = customer;
    this.product = product;
    this.order = order;
  }

  // 요구사항의 '마케팅 활용'을 위해 Soft Delete를 고려
  public void delete(String adminEmail) {
    if (this.deletedAt != null) {
      throw new IllegalStateException("이미 삭제 처리된 리뷰입니다.");
    }
    this.deletedAt = LocalDateTime.now();
    this.deletedBy = adminEmail; // 삭제한 관리자 기록
    this.deletedAt = LocalDateTime.now();
  }

  public String getCustomerName() {
    return (this.customer != null) ? this.customer.getName() : "알 수 없는 고객";
  }

  public String getProductName() {
    return (this.product != null) ? this.product.getName() : "삭제된 상품";
  }
}
