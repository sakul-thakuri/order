package com.example.order.controller;

import com.example.order.entity.Product;
import com.example.order.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/products")
public class ProductController {

    private ProductService productService;

    public ProductController (ProductService productService) {
        this.productService = productService;
    }

    @PostMapping ("")
    public ResponseEntity<?> addProduct (@RequestBody Product product) {
        boolean res = productService.addProduct(product);
        if(res) {
            return new ResponseEntity<>("product added successfully", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("error occurred", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getProducts () {
        List<Product> productList = productService.findAllProducts();
        if(productList == null) {
            return new ResponseEntity<>("products not found", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(productList);
    }
}

