package com.backoffice.customer.dto;

import com.backoffice.customer.entity.CustomerStatus;
import lombok.Getter;

@Getter
public class CustomerGetRequest {

  private String keyword; // 이름, 이메일
  private int page = 1; // 기본값 1
  private int pageSize = 10; // 기본값 10
  private String sortBy = "createdAt"; // 정렬 기준 -> 이름, 이메일, 가입일 -> 값이 있으면 덮어씌워짐/없으면 기본값(초기화)대로 들어감
  private String direction = "desc"; // 정렬 순서 -> asc, desc
  CustomerStatus status; // 상태 enum status

  // 상태 필터는 세션에서 가져와야할 것 같음..
  // sortBy 랑 direction 을 확인하고 꼭 그 값에 맞춰서 진행 하면됨
  // 아무것도 없는 값을 줄 가능성도 있으니까 기본값으로 갈 수 있게!!
  // 클라이언트가 넣은 값으로 하면 덮어씌워질 수 있음

}
