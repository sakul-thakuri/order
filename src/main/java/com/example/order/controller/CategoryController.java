package com.example.order.controller;

import com.example.order.entity.Categories;
import com.example.order.entity.Product;
import com.example.order.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/categories")
public class CategoryController {

    private CategoryService categoryService;

    public CategoryController (CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("")
    public ResponseEntity<?> addCategory (@RequestBody Categories category) {
        boolean res = categoryService.addCategory(category);
        if (res) {
            return new ResponseEntity<>("category added successfully", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("error occurred", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getCategories () {
         List<Categories> categories = categoryService.getCategories();
         if(categories.isEmpty()) {
             return new ResponseEntity<>("there is no category to show", HttpStatus.NOT_FOUND);
         }
         else {
             return ResponseEntity.ok(categories);
         }
    }

    @PutMapping("{categoryId}/products")
    public ResponseEntity<?> addProductToCategories (@PathVariable("categoryId") Long categoryId,
                                                     @RequestBody List<Long> productsId) {
        Categories category = categoryService.findCategory(categoryId);
        if(category == null) {
            return new ResponseEntity<>("category not found", HttpStatus.NOT_FOUND);
        }
        try {
            categoryService.addProductsToCategory(category, productsId);
            return new ResponseEntity<>("product added successfully", HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>("error occurred with provided category", HttpStatus.BAD_REQUEST);
        }
    }
}
