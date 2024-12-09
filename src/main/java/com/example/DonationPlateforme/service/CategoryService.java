package com.example.DonationPlateforme.service;

import com.example.DonationPlateforme.model.Category;
import com.example.DonationPlateforme.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }

}
