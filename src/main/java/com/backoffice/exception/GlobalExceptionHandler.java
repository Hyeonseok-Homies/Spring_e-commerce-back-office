package com.backoffice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // 클라이언트의 없는 로
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ErrorResponse> handlerNoSuchElementException(NoSuchElementException ex) {
    ErrorResponse response =
        new ErrorResponse(
            HttpStatus.UNAUTHORIZED.value(), ex.getMessage(), Collections.emptyList());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
  }

  // 권한 거부
  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ErrorResponse> handlerIllegalStateException(IllegalStateException ex) {
    ErrorResponse response =
        new ErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage(), Collections.emptyList());
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
  }

  // 없는 값 조회
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handlerIllegalArgumentException(
      IllegalArgumentException ex) {
    ErrorResponse response =
        new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Collections.emptyList());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  // 잘못된 접근
  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex) {
    ErrorResponse response =
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Collections.emptyList());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  // Valid 검출시 오류 핸들링
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException ex) {
    ErrorResponse response =
        new ErrorResponse(HttpStatus.BAD_REQUEST.value(), ex.getMessage(), Collections.emptyList());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }
}
