package com.backoffice.review.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReviewResponseDto {
  private List<ReviewListElementDto> reviewList; // 데이터 목록
  private int currentPage; // 현재 페이지 번호
  private int totalPageSize; // 전체 페이지 수
  private Long totalElements; // 전체 데이터 갯수
  private int pageSize; // 페이지당 요청했던 갯수
  private boolean hasNext; // 다음 페이지 존재 여부

  public ReviewResponseDto(List<ReviewListElementDto> reviewList, Page<?> page) {
    this.reviewList = reviewList;
    this.currentPage = page.getNumber() + 1;
    this.totalPageSize = page.getTotalPages();
    this.totalElements = page.getTotalElements();
    this.pageSize = page.getSize();
    this.hasNext = page.hasNext();
  }

  @Getter
  @Builder
  public static class ReviewListElementDto {
    private Long id;
    private Long orderNumber;
    private String customerName;
    private String productName;
    private int grade;
    private String reviewContent;
    private LocalDateTime createdAt;
  }
}
