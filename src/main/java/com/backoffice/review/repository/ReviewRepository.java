package com.backoffice.review.repository;

import com.backoffice.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long> {
  @Query(
      "SELECT r FROM Review r "
          + "WHERE (:keyword IS NULL OR r.reviewContent LIKE %:keyword%) "
          + "AND (:grade IS NULL OR r.grade = :grade)")
  Page<Review> searchReviews(
      @Param("keyword") String keyword, @Param("grade") Integer grade, Pageable pageable);
}
