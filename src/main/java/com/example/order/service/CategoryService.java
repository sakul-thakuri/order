package com.example.order.service;

import com.example.order.entity.Categories;
import com.example.order.entity.Product;
import com.example.order.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    private ProductService productService;
    Logger log = LoggerFactory.getLogger(CategoryService.class);

    public CategoryService (CategoryRepository categoryRepository, ProductService productService) {
        this.categoryRepository = categoryRepository;
        this.productService = productService;
    }

    public boolean addCategory(Categories category) {
        if(categoryRepository.findByName(category.getName())!= null) {
            return false;
        }
        try {
            categoryRepository.save(category);
            return true;
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
            log.error("error adding category");
            return false;
        }
    }

    public List<Categories> getCategories() {
        return categoryRepository.findAll();
    }

    public Categories findCategory(Long id) {
        Optional<Categories> category = categoryRepository.findById(id);
        return category.orElse(null);
    }

    public void addProductsToCategory(Categories category, List<Long> productsId) throws IllegalArgumentException {
        Set<Product> productList = category.getProductList();
        for(Long productId : productsId) {
            Product product = productService.findById(productId);
            if(product == null) {
                continue;
            }
            Set<Categories> categoriesSet = product.getCategories();
            categoriesSet.add(category);
            productList.add(product);
        }
        try {
            categoryRepository.save(category);
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
            throw new IllegalArgumentException(e);
        }

    }
}
