package com.backoffice.admin.dto;

import com.backoffice.admin.entity.AdminRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class AdminSessionDto implements Serializable { // 세션 저장 시 직렬화 필요 [Serializable 이게 왜 필요한지 찾아보기] 테스트 하는 방법이 있다 찾아보기
    private Long id;
    private String email;
    private AdminRole role;
}