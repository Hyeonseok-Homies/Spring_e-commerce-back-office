package com.backoffice.product.dto;

import lombok.Getter;


@Getter
public class ProductCreateRequest {
  private String name;
  private String category;
  private Long price;
  private Long stock;
  private String status;
}

