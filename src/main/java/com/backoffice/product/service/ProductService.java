package com.backoffice.product.service;

import com.backoffice.product.dto.ProductCreateRequest;
import com.backoffice.product.dto.ProductCreateResponse;
import com.backoffice.product.dto.ProductGetResponse;
import com.backoffice.product.entity.Product;
import com.backoffice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
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
}
