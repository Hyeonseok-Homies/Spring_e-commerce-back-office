package com.backoffice.product.service;

import com.backoffice.product.dto.*;
import com.backoffice.product.entity.Product;
import com.backoffice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductService {

  private final ProductRepository productRepository;

  @Transactional
  public ProductCreateResponse create(ProductCreateRequest request) {
    Product product =
        new Product(
            request.getName(),
            request.getPrice(),
            request.getCategory(),
            request.getStock(),
            request.getStatus());
    Product savedProduct = productRepository.save(product);
    return new ProductCreateResponse(
        savedProduct.getId(),
        savedProduct.getName(),
        savedProduct.getCategory(),
        savedProduct.getPrice(),
        savedProduct.getStock(),
        savedProduct.getStatus(),
        savedProduct.getCreateAt(),
        savedProduct.getUpdatedAt(),
        savedProduct.getCreatedByAdminId());
  }

  @Transactional(readOnly = true)
  public List<ProductGetResponse> getAll() {
    List<Product> products = productRepository.findAll();
    return products.stream()
        .map(
            product ->
                new ProductGetResponse(
                    product.getId(),
                    product.getName(),
                    product.getCategory(),
                    product.getPrice(),
                    product.getStock(),
                    product.getStatus(),
                    product.getCreateAt(),
                    product.getUpdatedAt(),
                    product.getCreatedByAdminId()))
        .toList();
  }

  @Transactional(readOnly = true)
  public ProductGetResponse getOne(Long id) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 물품 입니다."));
    return new ProductGetResponse(
        product.getId(),
        product.getName(),
        product.getCategory(),
        product.getPrice(),
        product.getStock(),
        product.getStatus(),
        product.getCreateAt(),
        product.getUpdatedAt(),
        product.getCreatedByAdminId());
  }

  @Transactional(readOnly = true)
  public @Nullable ProductUpdateResponse update(Long id, ProductUpdateRequest request) {
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
        product.getCreateAt(),
        product.getUpdatedAt(),
        product.getCreatedByAdminId());
  }

  public void delete(Long id) {
    Product product =
        productRepository
            .findById(id)
            .orElseThrow(() -> new IllegalStateException("존재하지 않는 물품 입니다."));
    productRepository.delete(product);
  }
}
