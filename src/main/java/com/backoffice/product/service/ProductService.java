package com.backoffice.product.service;

import com.backoffice.product.dto.ProductCreateRequest;
import com.backoffice.product.dto.ProductCreateResponse;
import com.backoffice.product.dto.ProductGetResponse;
import com.backoffice.product.entity.Product;
import com.backoffice.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
      List<Product> product = productRepository.findAll();
    }
}
