package com.example.DonationPlateforme.controller;

import com.example.DonationPlateforme.model.Category;
import com.example.DonationPlateforme.service.CategoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.findAllCategories();
    }

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable("id") UUID id) {
        categoryService.deleteCategory(id);
    }

}
