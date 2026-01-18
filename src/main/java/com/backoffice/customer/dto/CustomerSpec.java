package com.backoffice.customer.dto;

import com.backoffice.customer.entity.Customer;
import com.backoffice.customer.entity.CustomerStatus;
import org.hibernate.tool.schema.internal.exec.ScriptSourceInputFromReader;
import org.springframework.data.jpa.domain.Specification;
import tools.jackson.databind.util.ArrayBuilders;

public class CustomerSpec {

    // Enum 상태 필터 (활성, 비활성, 정지)
    public static Specification<Customer> equalStatus(CustomerStatus status) {
        return (root, query, builder) -> {
            if (status == null) return null;
            return builder.equal(root.get("status"), status);
        };
    }

    // 검색어 필터 (이름 or 이메일)
    public static Specification<Customer> searchByKeyword(String keyword) {
        return (root, query, builder) -> {

            // 키워드가 없을 시 필터 적용아지 않음 (전체 조회)
            if (keyword == null || keyword.trim().isEmpty()) {
                return null;
            }

            String pattern = "%" + keyword + "%";

            // 이름 또는 이메일에 필터가 포함되어있는지 검사
            return builder.or(
                    builder.like(root.get("name"), pattern),
                    builder.like(root.get("email"), pattern)
            );
        };
    }
}
