package com.backoffice.customer.dto;

import com.backoffice.customer.entity.Customer;
import com.backoffice.customer.entity.CustomerStatus;
import org.springframework.data.jpa.domain.Specification;

public class CustomerSpec {

  // 상태 필터 (활성, 비활성, 정지)
  public static Specification<Customer> equalStatus(CustomerStatus status) {
    return (root, query, builder) -> builder.equal(root.get("status"), status);
  }

  // 검색어 필터 (이름 or 이메일)
  public static Specification<Customer> searchByKeyword(String keyword) {
    return (root, query, builder) ->
        builder.or(
            builder.like(root.<String>get("name"), keyword),
            builder.like(root.<String>get("email"), keyword));
  }
}
