package org.sopt.practice.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.practice.service.ProductService;
import org.sopt.practice.domain.Product;
import org.sopt.practice.dto.product.ProductAddDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody ProductAddDto productAddDto){
        return ResponseEntity.ok(productService.addProduct(productAddDto));
    }
}
