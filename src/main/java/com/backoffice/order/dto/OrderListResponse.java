package com.backoffice.order.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class OrderListResponse<T> {
  private final List<T> items;

  private final int page; // 현재 페이지(1-base)
  private final int size; // 페이지당 개수
  private final long totalItems; // 전체 개수
  private final int totalPages; // 전체 페이지 수

  public OrderListResponse(Page<T> pageResult) {
    this.items = pageResult.getContent();
    this.page = pageResult.getNumber() + 1;
    this.size = pageResult.getSize();
    this.totalItems = pageResult.getTotalElements();
    this.totalPages = pageResult.getTotalPages();
  }
}
