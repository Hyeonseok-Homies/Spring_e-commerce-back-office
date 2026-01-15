package com.backoffice.product.service;

import com.backoffice.admin.entity.Admin;
import com.backoffice.admin.repository.AdminRepository;
import com.backoffice.product.dto.*;
import com.backoffice.product.entity.Product;
import com.backoffice.product.entity.ProductStatus;
import com.backoffice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;
  private final AdminRepository adminRepository;

  @Transactional
  public ProductCreateResponse create(ProductCreateRequest request, Long adminId) {
    Admin admin =
        adminRepository
            .findById(adminId)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 어드민입니다."));
    Product product =
        new Product(
            request.getName(),
            request.getPrice(),
            request.getCategory(),
            request.getStock(),
            request.getStatus(),
            admin);
    Product savedProduct = productRepository.save(product);
    return new ProductCreateResponse(savedProduct);
  }

  @Transactional(readOnly = true)
  public Page<ProductGetResponse> getAll(
      String name,
      int page,
      int size,
      String sortBy,
      String direction,
      String category,
      ProductStatus status) {

    Sort.Direction sortDirection =
        direction.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

    Pageable pageable = PageRequest.of(page - 1, size, Sort.by(sortDirection, sortBy));

    Page<Product> products = productRepository.findAllWithFilters(name, category, status, pageable);

    return products.map(ProductGetResponse::new);
  }

  @Transactional(readOnly = true)
  public ProductGetResponse getOne(Long id) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 물품 입니다."));
    return new ProductGetResponse(product);
  }

  @Transactional
  public ProductUpdateResponse update(Long id, ProductUpdateRequest request) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 물품 입니다."));

    product.update(
        request.getName(),
        request.getPrice(),
        request.getCategory(),
        request.getStock(),
        request.getStatus());
    return new ProductUpdateResponse(product);
  }

  public void delete(Long id) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 물품 입니다."));
    productRepository.delete(product);
  }
}
