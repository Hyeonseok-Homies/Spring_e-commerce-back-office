package com.backoffice.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
  @NotBlank
  private Long id;

  @NotBlank
  @Column(nullable = false, length = 50)
  private String name;

  private String category;
  private Long price;
  private Long stock;
  private String status;
  private Long createdByAdminId;

  public Product(String name, Long price, String category, Long stock, String status) {
    this.name = name;
    this.price = price;
    this.category = category;
    this.stock = stock;
    this.status = status;
    this.createdByAdminId = createdByAdminId;
  }
}
