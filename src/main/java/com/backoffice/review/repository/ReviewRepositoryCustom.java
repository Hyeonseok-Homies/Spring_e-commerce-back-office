package com.backoffice.review.repository;

import com.backoffice.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
  // 서비스에서 호출할 동적 검색 메서드 정의
  Page<Review> searchReviews(String keyword, Integer grade, Pageable pageable);
}
