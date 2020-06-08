package com.example.order.service;

import com.example.order.entity.Categories;
import com.example.order.entity.Product;
import com.example.order.repository.CategoryRepository;
import com.example.order.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {

    private ProductRepository productRepository;
    private CategoryRepository categoryRepository;
    private Logger log = LoggerFactory.getLogger(ProductService.class);

    public ProductService (ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }


    public boolean addProduct(Product product) {
        if(productRepository.findByName(product.getName())!= null) {
            return false;
        }
        Set<Categories> categories = new HashSet<>();
        for(Categories category : product.getCategories()) {
            if(!categoryRepository.findById(category.getId()).isPresent()) {
                return false;
            }
            Categories existingCategory = categoryRepository.findById(category.getId()).get();
            Set<Product> categoryProductList = existingCategory.getProductList();
            categoryProductList.add(product);
            categories.add(existingCategory);
        }
        product.setCategories(categories);
        try {
            productRepository.save(product);
            return true;
        }
        catch (IllegalArgumentException e) {
            e.printStackTrace();
            log.error("error adding product");
            return false;
        }
    }

    public Product findProductByProductName (String productName) {
            return productRepository.findByName(productName);
    }

    public Product findById (Long id) {
        Optional<Product> product = productRepository.findById(id);
        return product.orElse(null);
    }

    public List<Product> findAllProducts () {
        return productRepository.findAll();
    }
}
