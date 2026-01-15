package com.backoffice.customer.controller;

import com.backoffice.customer.dto.*;
import com.backoffice.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // 고객 전체 조회
    @GetMapping("/api/customers")
    public ResponseEntity<Page<CustomerGetResponse>> getAll(
            // CustomerGetRequest 정렬조건을 request에 담아서 불러옴
            CustomerGetRequest request,
            @PageableDefault (size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.findAll(request, pageable));
    }

    // 고객 상세 (단건) 조회
    @GetMapping("/api/customers/{id}")
    public ResponseEntity<CustomerGetResponse> getById(
            @PathVariable Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findById(id));
    }

    // 고객 정보 업데이트
    @PutMapping("/api/customers/{id}")
    public ResponseEntity<CustomerUpdateResponse> update(
            @PathVariable Long id,
            @RequestBody CustomerUpdateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.update(id, request));
    }

    // 고객 정보 삭제(탈퇴)
    @DeleteMapping("/api/customers/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @RequestBody CustomerDeleteRequest request
    ) {
       return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 상태 변경
    @PatchMapping ("/api/customers/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestBody CustomerStatusUpdateRequest request
    ) {
       return ResponseEntity.status(HttpStatus.OK).build();
    }
}
