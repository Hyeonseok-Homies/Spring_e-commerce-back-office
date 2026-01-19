package com.backoffice.customer.controller;

import com.backoffice.admin.config.Login;
import com.backoffice.admin.dto.SessionAdmin;
import com.backoffice.customer.dto.*;
import com.backoffice.customer.entity.CustomerStatus;
import com.backoffice.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/customers")
@RestController
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    // 고객 전체 조회
    @GetMapping
    public ResponseEntity<Page<CustomerGetResponse>> getAll(
            @Login SessionAdmin sessionAdmin,
            // CustomerGetRequest 정렬조건을 request에 담아서 불러옴
//      @ModelAttribute CustomerGetRequest request,
            @RequestParam String keyword,
            @RequestParam CustomerStatus status,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findAll(new CustomerGetRequest(), pageable));
    }

    // 고객 상세 (단건) 조회
    @GetMapping("/{id}")
    public ResponseEntity<CustomerGetResponse> getById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.findById(id));
    }

    // 고객 정보 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<CustomerUpdateResponse> update(
            @Login SessionAdmin sessionAdmin,
            @PathVariable Long id,
            @RequestBody CustomerUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.update(id, request));
    }

    // 고객 정보 삭제(탈퇴)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {
        customerService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 상태 변경
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long id,
            @RequestBody CustomerStatusUpdateRequest request
    ) {
        customerService.updateStatus(id, request.getStatus());
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
