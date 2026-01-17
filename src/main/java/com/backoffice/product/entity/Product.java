package com.backoffice.product.entity;

import com.backoffice.admin.entity.Admin;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "products")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Column(nullable = false, length = 50)
  private String name;

  @NotBlank
  @Column(nullable = false, length = 50)
  private String category;

  @NotNull private Long price;
  private Long stock;

  @Enumerated(EnumType.STRING)
  private ProductStatus status;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "Created_By_Admin_Id")
  private Admin admin;

  public Product(
      String name, Long price, String category, Long stock, ProductStatus status, Admin admin) {
    this.name = name;
    this.price = price;
    this.category = category;
    this.stock = stock;
    this.status = status;
    this.admin = admin;
  }

  public void update(String name, Long price, String category, Long stock, ProductStatus status) {
    this.name = name;
    this.price = price;
    this.category = category;
    this.stock = stock;

    if (this.status == ProductStatus.DISCONTINUED || status == ProductStatus.DISCONTINUED) {
      this.status = ProductStatus.DISCONTINUED;
    } else if (this.stock <= 0) {
      this.status = ProductStatus.SOLDOUT;
    } else {
      this.status = ProductStatus.ONSALE;
    }
  }

  public void removeStock(Long quantity) {
    if (this.stock < quantity) {
      throw new IllegalStateException("재고가 부족합니다. (요청: " + quantity + ", 현재: " + this.stock + ")");
    }
    this.stock -= quantity;
    updateStatus(); // 재고에 따라 상태 변경
  }

  private void updateStatus() {
    if (this.status == ProductStatus.DISCONTINUED) {
      return;
    }
    if (this.stock == 0) {
      this.status = ProductStatus.SOLDOUT;
    } else {
      this.status = ProductStatus.ONSALE;
    }
  }

  public void addStock(Long quantity) {
    if (quantity == null || quantity <= 0) {
      throw new IllegalArgumentException("복구할 수량은 0보다 커야 합니다.");
    }
    this.stock += quantity;
    updateStatus();
  }
}
