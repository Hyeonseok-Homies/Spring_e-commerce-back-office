package com.backoffice.admin.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor // final 생성자 자동생성
public enum AdminRole {
  SUPER("슈퍼 관리자"),
  OPERATOR("운영 관리자"),
  CS("CS 관리자");

  private final String description; // 입력받은 description에 해당하는 역할부여
}
