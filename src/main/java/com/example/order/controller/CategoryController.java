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
}
