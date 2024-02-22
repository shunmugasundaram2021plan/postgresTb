package com.sb.postgresTb.controller;

import com.sb.postgresTb.model.Product;
import com.sb.postgresTb.repository.ProductRepository;
import com.sb.postgresTb.service.ProductService;
import com.sb.postgresTb.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        productService.ExcelGeneratorDB();

        List<Product> products = this.productRepository.findAll();
        return ResponseEntity.ok(products);
    }

}
