package com.example.final_project.service;

import com.example.final_project.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import com.example.final_project.dto.CategoryListDto;


public interface CategoryService {

    Page<CategoryListDto> findAllPage(Pageable pageable);

   // List<Category> findAll();

    List<CategoryListDto> findAll();


    Optional<Category> findById(Long id);

    Optional<Category> findByName(String name);

    Category save(Category category);

    void deleteById(Long id);
}