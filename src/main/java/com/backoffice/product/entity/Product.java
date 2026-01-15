package com.backoffice.product.entity;

import com.backoffice.admin.entity.Admin;
import jakarta.persistence.*;
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
  @NotNull
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
}
