package org.example.project.service.other.impl;

import jakarta.transaction.Transactional;
import org.example.project.model.CategoryDTO;
import org.example.project.repository.other.CategoryRepository;
import org.example.project.repository.other.ProductRepository;
import org.example.project.service.other.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public CategoryDTO getCategory(String email,String name) {

//        String byName = productRepository.findCategoryByName(email, name);
        int countCategoryByName = productRepository.findCountCategoryByName(email, name);
        if ( countCategoryByName >0) {
            CategoryDTO categories = new CategoryDTO(countCategoryByName, name);
            return categories;
        } else{
            return new CategoryDTO(0, name);
        }
    }
}
