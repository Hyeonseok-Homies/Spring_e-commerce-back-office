package com.backoffice.product.service;

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

  // 상품 등록 TODO 상품 게시자 ID 자동으로 받아오는거 도대체 HOW?
  @Transactional
  public ProductCreateResponse create(ProductCreateRequest request) {
    Product product =
        new Product(
            request.getName(),
            request.getPrice(),
            request.getCategory(),
            request.getStock(),
            request.getStatus()); // 품절, 단종, 판매중 상태를 받아야함
    Product savedProduct = productRepository.save(product);
    return new ProductCreateResponse(
        savedProduct.getId(),
        savedProduct.getName(),
        savedProduct.getCategory(),
        savedProduct.getPrice(),
        savedProduct.getStock(),
        savedProduct.getStatus(),
        savedProduct.getCreatedAt(), // 제품 게시일
        savedProduct.getUpdatedAt(), // 제품 수정일
        savedProduct.getCreatedByAdminId()); // 외래키를 통해서 자동으로 받아오게 해야함 HOW?
  }

  // 상품 리스트 조회
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

    return products.map(
        product ->
            new ProductGetResponse(
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock(),
                product.getStatus(),
                product.getCreatedAt(),
                product.getCreatedByAdminId()));
  }

  // 상품 상세 조회
  @Transactional(readOnly = true)
  public ProductGetResponse getOne(Long id) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 물품 입니다."));
    return new ProductGetResponse(
        product.getName(),
        product.getCategory(),
        product.getPrice(),
        product.getStock(),
        product.getStatus(),
        product.getCreatedAt(),
        product.getCreatedByAdminId());
  }

  // 상품 정보 수정 Clear
  // 상품명, 카테고리, 가격, 상품 상태 변경 가능
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
    return new ProductUpdateResponse(
        product.getId(),
        product.getName(),
        product.getCategory(),
        product.getPrice(),
        product.getStock(),
        product.getStatus(),
        product.getCreatedAt(),
        product.getUpdatedAt(),
        product.getCreatedByAdminId());
  }

  // 상품 삭제 Clear
  public void delete(Long id) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 물품 입니다."));
    productRepository.delete(product);
  }
}
