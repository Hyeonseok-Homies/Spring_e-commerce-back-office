package com.backoffice.customer.repository;

import com.backoffice.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CustomerRepository
    extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
  // 검색 키워드 - 이름, 이메일
  // Page<Customer> findByNameContainingOrEmailContaining(String name, String email, Pageable pageable);
  // findAll 메서드에 Pageable을 넣어서 페이징 처리
  // Page<Customer> findAll(Pageable pageable);
}
