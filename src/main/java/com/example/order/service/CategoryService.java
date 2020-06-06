package com.example.order.service;

import com.example.order.entity.Categories;
import com.example.order.repository.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private CategoryRepository categoryRepository;
    Logger log = LoggerFactory.getLogger(CategoryService.class);

    public CategoryService (CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
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
}
