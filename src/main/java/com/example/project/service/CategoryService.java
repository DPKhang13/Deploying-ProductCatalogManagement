package com.example.project.service;

import com.example.project.dto.request.CategoryRequest;
import com.example.project.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse createCategory(CategoryRequest request);
    List<CategoryResponse> getAllCategory();
    CategoryResponse updateCategory (Long categoryId, CategoryRequest request);
    void deleteCategory (Long categoryId);
}
