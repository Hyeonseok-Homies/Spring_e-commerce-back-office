package com.backoffice.admin.dto;

import com.backoffice.admin.entity.Admin;
import com.backoffice.admin.entity.AdminRole;
import com.backoffice.admin.entity.AdminStatus;
import lombok.Getter;

@Getter
public class AdminListRequestDto {
  // 아래 셋팅된 기본값은, 요청시 별도의 값이 없다면 기본값으로 활용
  private String keyword; // 검색 키워드 이름,이메일
  private int page = 1;
  private int pageSize = 10;
  private String sortBy = "createdAt"; // 정렬기준 createdAt,name,email
  private String direction = "desc"; // 정렬 순서 asc, desc
  // 로그인시 세션정보에서 역할과 상태값을 가져오는걸로
  private AdminRole role; // 역할 필터 슈퍼 관리자, 운영 관리자, CS 관리자
  private AdminStatus status; // 상태 필터 활성, 비활성, 정지, 승인대기, 거부
}
