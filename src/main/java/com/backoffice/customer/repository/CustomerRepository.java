package com.backoffice.customer.repository;

import com.backoffice.customer.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // 검색 키워드 - 이름, 이메일
    Page<Customer> findByNameContainingOrEmailContaining(String name, String email, Pageable pageable);
    // findAll 메서드에 Pageable을 넣어서 페이징 처리
    Page<Customer> findAll(Pageable pageable);
}
