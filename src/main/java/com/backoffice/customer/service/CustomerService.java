package com.backoffice.customer.service;

import com.backoffice.customer.dto.*;
import com.backoffice.customer.entity.Customer;
import com.backoffice.customer.entity.CustomerStatus;
import com.backoffice.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer;

    // 고객 전체 조회
    @Transactional(readOnly = true)
    // 정렬 기준 List -> Page 로 변경 (page 확인을 위해서 변경)
    public Page<CustomerGetResponse> findAll(CustomerGetRequest request, Pageable pageable) {

        // 검색 조건 초기화 - 동적 쿼리 생성
        Specification<Customer> spec = Specification.anyOf();

        // 키워드(이름, 이메일)가 null이 아니고 비어있지도 않을 때 -> 키워드가 있을 때
        // 이름 조건 keyword, 이메일 조건 keyword 가 포함된 데이터만 필터링해서 각각 넣어주기 위해서 설정
        if (request.getKeyword() != null && !request.getKeyword().trim().isEmpty()) {
            spec = spec.and(CustomerSpec.searchByKeyword(request.getKeyword()));
        }

        // 상태 status 필터
        if (request.getStatus() != null) {
            spec = spec.and(CustomerSpec.equalStatus(request.getStatus()));
        }

        Page<Customer> customers = customerRepository.findAll(spec, pageable);

        return customers.map(
                customer ->
                        new CustomerGetResponse(
                                customer.getId(),
                                customer.getName(),
                                customer.getEmail(),
                                customer.getStatus(),
                                customer.getCreatedAt(),
                                customer.getTotalOrders(),
                                customer.getTotalPurchaseAmount()));
    }

  // 고객 상세 (단건) 조회
  @Transactional(readOnly = true)
  public CustomerGetResponse findById(Long id) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 고객이 없습니다."));

        return new CustomerGetResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getStatus(),
                customer.getCreatedAt(),
                customer.getTotalOrders(),
                customer.getTotalPurchaseAmount());
    }

  // 고객 정보 수정
  @Transactional
  public CustomerUpdateResponse update(Long id, CustomerUpdateRequest request) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 고객이 없습니다."));

        customer.update(request.getName(), request.getEmail(), request.getPhoneNumber());

        return new CustomerUpdateResponse(
                customer.getId(), customer.getName(), customer.getEmail(), customer.getPhoneNumber());
    }

  // 고객 삭제
  @Transactional
  public void delete(Long id, CustomerDeleteRequest request) {
    Customer customer =
        customerRepository
            .findById(id)
            .orElseThrow(() -> new IllegalArgumentException("해당 고객이 없습니다."));
    customerRepository.delete(customer);
  }

    // 상태 변경
    @Transactional
    public void updateStatus(Long id, CustomerStatus status) {
        // 찾는 고객이 없을때
        Customer customer =
                customerRepository
                        .findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("해당 고객이 없습니다."));
        // 찾는 고객이 있을 때
        customer.updateStatus(status);
    }
}
