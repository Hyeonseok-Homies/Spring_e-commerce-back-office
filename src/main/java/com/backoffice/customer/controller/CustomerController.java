package com.backoffice.customer.controller;

import com.backoffice.customer.dto.*;
import com.backoffice.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            CustomerGetRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerService.findAll(request));
    }

    // 고객 상세 (단건) 조회
    @GetMapping("/api/customers/{id}")
    public ResponseEntity<CustomerGetResponse> getById(
            @PathVariable Long customerId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findById(customerId));
    }

    // 고객 정보 업데이트
    @PutMapping("/api/customers/{id}")
    public ResponseEntity<CustomerUpdateResponse> update(
            @PathVariable Long customerId,
            @RequestBody CustomerUpdateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.update(customerId, request));
    }

    // 고객 정보 삭제(탈퇴)
    @DeleteMapping("/api/customers/{id}")
    public void delete(
            @PathVariable Long customerId,
            @RequestBody CustomerDeleteRequest request
    ) {
        customerService.delete(customerId, request);
    }

    // 상태 변경
    @PatchMapping ("/api/customers/{id}/status")
    public void updateStatus(
            @PathVariable Long customerId,
            @RequestBody CustomerStatusUpdateRequest request
    ) {
        customerService.updateStatus(customerId, request.getStatus());
    }
}
