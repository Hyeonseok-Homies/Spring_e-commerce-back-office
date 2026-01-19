package com.backoffice.customer.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class CustomerPageResponse {

  private List<CustomerGetResponse> customerList; // 데이터 목록

  private int page; // 현재 페이지
  private int totalPageSize; // 전체 페이지 수
  private Long totalElements; // 전체 데이터 수
  private int pageSize; // 화면 하단 페이지당 출력할 페이지 수

  public CustomerPageResponse(
      List<CustomerGetResponse> customerList,
      int page,
      int totalPageSize,
      Long totalElements,
      int pageSize) {
    this.customerList = customerList;
    this.page = page;
    this.totalPageSize = totalPageSize;
    this.totalElements = totalElements;
    this.pageSize = pageSize;
  }
}
