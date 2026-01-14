package com.backoffice.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @Enumerated(EnumType.STRING) // Enum을 DB에 문자열로 저장
    private ProductStatus status;  private Long createdByAdminId;

  public Product(String name, Long price, String category, Long stock, ProductStatus status) {
    this.name = name;
    this.price = price;
    this.category = category;
    this.stock = stock;
    this.status = status;
    this.createdByAdminId = createdByAdminId;
  }

    public void update(String name, Long price, String category, Long stock, ProductStatus status) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.stock = stock;

        // 1. 단종 체크: 기존 상태가 단종이거나, 요청 상태가 단종인 경우
        if (this.status == ProductStatus.DISCONTINUED || status == ProductStatus.DISCONTINUED) {
            this.status = ProductStatus.DISCONTINUED;
        }
        // 2. 재고 체크: 재고가 0 이하면 무조건 품절
        else if (this.stock <= 0) {
            this.status = ProductStatus.SOLDOUT;
        }
        // 3. 그 외: 판매중으로 전환
        else {
            this.status = ProductStatus.ONSALE;
        }
    }
}
