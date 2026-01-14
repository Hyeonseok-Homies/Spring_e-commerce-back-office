package com.backoffice.product.repository;

import com.backoffice.product.entity.Product;
import com.backoffice.product.entity.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Page<Product> GetAll(String name, Pageable pageable);

  Page<Product> findAllWithFilters(
      @Param("name") String name,
      @Param("category") String category,
      @Param("status") ProductStatus status,
      Pageable pageable);
}
