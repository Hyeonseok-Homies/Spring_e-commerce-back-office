package com.backoffice.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllDto<T> {

  private int status; // HTTP 상태 코드 (예: 200, 201)
  private String message; // 응답 메시지 (예: "조회 성공")
  private T data; // 실제 반환 데이터 (객체 혹은 리스트)

  public static <T> AllDto<T> success(int status, String message, T data) {

    return new AllDto<>(status, message, data);
  }
}
