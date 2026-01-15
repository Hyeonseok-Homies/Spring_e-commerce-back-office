package com.backoffice.admin.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class AdminListResponseDto {
    private List<AdminResponseDto> adminList;//데이터 목록

    private int currentPage;//현재 페이지 번호
    private int totalPageSize;//전체 페이지 수
    private Long totalElements;//전체 데이터 갯수
    private int pageSize;//페이지당 요청했던 갯수
    private boolean hasNext;//다음 페이지 존재 여부?//검색내용인데 확인해보기
    public AdminListResponseDto(List<AdminResponseDto> adminList, int currentPage, int totalPageSize,
                                Long totalElements, int pageSize, boolean hasNext) {
        this.adminList = adminList;
        this.currentPage = currentPage;
        this.totalPageSize = totalPageSize;
        this.totalElements = totalElements;
        this.pageSize = pageSize;
        this.hasNext = hasNext;
    }
}
