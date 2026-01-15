package com.backoffice.customer.service;

import com.backoffice.customer.dto.*;
import com.backoffice.customer.entity.Customer;
import com.backoffice.customer.entity.CustomerStatus;
import com.backoffice.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer;

    // 고객 전체 조회
    @Transactional(readOnly = true)
    // 정렬 기준 List -> Page 로 변경 (page 확인을 위해서 변경)
    public Page<CustomerGetResponse> findAll(CustomerGetRequest request) {

        // 페이징 설정 (정렬 방향 + 변환된 컬럼명)
        Pageable pageable = PageRequest.of(
                request.getPage() - 1,
                request.getPageSize(),
                Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy()));

        // 키워드(이름, 이메일)가 null이 아니고 비어있지도 않을 때 -> 키워드가 있을 때
        // 이름 조건 keyword, 이메일 조건 keyword 가 포함된 데이터만 필터링해서 각각 넣어주기 위해서 설정
        Page<Customer> customers;
        if (request.getKeyword() != null && !request.getKeyword().isEmpty()) {
            customers = customerRepository.findByNameContainingOrEmailContaining(
                    request.getKeyword(),
                    request.getKeyword(),
                    pageable
            );
        } else {    // 키워드가 없을 때 (전체 조회 모드)
            customers = customerRepository.findAll(pageable);
        }

        return customers.map(customer -> new CustomerGetResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getStatus(),
                customer.getCreatedAt()
        ));
    }

    // 고객 상세 (단건) 조회
    @Transactional(readOnly = true)
    public CustomerGetResponse findById(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalStateException("해당 고객이 없습니다.")
        );

        return new CustomerGetResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getStatus(),
                customer.getCreatedAt()
        );
    }

    // 고객 정보 수정
    @Transactional
    public CustomerUpdateResponse update(Long customerId, CustomerUpdateRequest request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalStateException("해당 고객이 없습니다.")
        );

        customer.update(
                request.getName(),
                request.getEmail(),
                request.getPhoneNumber()
        );

        return new CustomerUpdateResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPhoneNumber()
        );
    }

    // 고객 삭제
    @Transactional
    public void delete(Long customerId, CustomerDeleteRequest request) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalStateException("해당 고객이 없습니다.")
        );
        customerRepository.delete(customer);

    }

    // 상태 변경
    @Transactional
    public void updateStatus(Long customerId, CustomerStatus status) {
        // 찾는 고객이 없을때
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new IllegalArgumentException("해당 고객이 없습니다.")
        );
        // 찾는 고객이 있을 때
        customer.updateStatus(status);
    }
}

