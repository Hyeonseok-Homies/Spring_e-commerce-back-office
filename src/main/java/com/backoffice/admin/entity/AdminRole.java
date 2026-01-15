package com.backoffice.admin.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor//final 생성자 자동생성
public enum AdminRole {
    SUPER("슈퍼 관리자"),
    OPERATOR("운영 관리자"),
    CS("CS 관리자");

    private final String description;// 각 상태에 대한 한글 설명(메타데이터) 저장
}
