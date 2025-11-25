package com.example.final_project.service;

import com.example.final_project.dto.CategoryListDto;
import com.example.final_project.dto.CategoryRequest;
import com.example.final_project.dto.CategorySearchRequest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    Page<CategoryListDto> searchCategories(CategorySearchRequest request);
    List<CategoryListDto> getAllCategories();
    Optional<CategoryListDto> getCategoryById(Long id);
    CategoryListDto saveCategory(CategoryRequest categoryRequest);
    void deleteCategoryById(Long id);
}