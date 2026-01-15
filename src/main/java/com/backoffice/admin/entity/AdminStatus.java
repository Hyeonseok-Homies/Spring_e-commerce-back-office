package com.backoffice.admin.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor // final 생성자 자동생성
public enum AdminStatus {
  ACTIVE("활성"),
  INACTIVE("비활성"),
  SUSPENDED("정지"),
  PENDING("승인대기"),
  REJECTED("거부");

  private final String description;// 각 상태에 대한 한글 설명(메타데이터) 저장
}
