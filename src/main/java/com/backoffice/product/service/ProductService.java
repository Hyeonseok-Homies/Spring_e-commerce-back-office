package com.backoffice.product.service;

import com.backoffice.product.dto.*;
import com.backoffice.product.entity.Product;
import com.backoffice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  // 상품 등록
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
        savedProduct.getCreateAt(), // 제품 게시일
        savedProduct.getUpdatedAt(), // 제품 수정일
        savedProduct.getCreatedByAdminId()); // 외래키를 통해서 자동으로 받아오게 해야함 HOW?
  }

  // 상품 리스트 조회 TODO 쿼리파라미터 지원하게 만들기
  @Transactional(readOnly = true)
  public List<ProductGetResponse> getAll() {
    List<Product> products = productRepository.findAll();
    return products.stream()
        .map(
            product ->
                new ProductGetResponse(
                    product.getName(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getStock(),
                    product.getStatus(),
                    product.getCreateAt(),
                    product.getCreatedByAdminId()))
        .toList();
  }

  /*
  요구사항

  다음 쿼리 파라미터를 지원해야 합니다.

  - 검색 키워드 (상품명)
  - 페이지 번호 (기본값: 1)
  - 페이지당 개수 (기본값: 10)
  - 정렬 기준 (예: 가격, 재고, 등록일)
  - 정렬 순서 (asc, desc)
  - 카테고리 필터 (예: 전자기기, 패션/의류, 식품)
  - 상태 필터 (`판매중`, `품절`, `단종`)

  응답에는 다음이 포함되어야 합니다.

  - 상품 목록
      - `고유 식별자(ID)`, `상품명`, `카테고리`, `가격`, `재고`, `상태`, `등록일`, `등록 관리자명`
  - 페이징 정보 (현재 페이지, 페이지당 개수, 전체 개수, 전체 페이지 수)
  해설 강의 8번
   */

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
        product.getCreateAt(),
        product.getCreatedByAdminId());
  }

  // 상품 정보 수정
  // 상품명, 카테고리, 가격, 상품 상태 변경 가능
  @Transactional // 수정을 위해 readOnly = true 제거
  public ProductUpdateResponse update(Long id, ProductUpdateRequest request) {
      // 1. 상품 존재 여부 확인
      Product product = productRepository.findById(id)
              .orElseThrow(() -> new IllegalStateException("존재하지 않는 물품 입니다."));

      // 2. 엔티티 내부의 통합 로직 호출 (정보 수정 + 재고/상태 정합성 체크)
      product.update(
              request.getName(),
              request.getPrice(),
              request.getCategory(),
              request.getStock(),
              request.getStatus()
      );

      // 3. 수정된 결과 반환
      return new ProductUpdateResponse(
              product.getId(),
              product.getName(),
              product.getCategory(),
              product.getPrice(),
              product.getStock(),
              product.getStatus(),
              product.getCreateAt(),
              product.getUpdatedAt(),
              product.getCreatedByAdminId()
      );
  }
  // 상품 삭제
  public void delete(Long id) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 물품 입니다."));
    productRepository.delete(product);
  }
}
