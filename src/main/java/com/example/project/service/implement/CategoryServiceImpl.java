package com.example.project.service.implement;

import com.example.project.dto.request.CategoryRequest;
import com.example.project.dto.response.CategoryResponse;
import com.example.project.entity.Category;
import com.example.project.repository.CategoryRepository;
import com.example.project.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse createCategory (CategoryRequest request) {
        if (categoryRepository.existsByName(request.getCategoryName())) {
            throw new IllegalStateException("CATEGORY_ALREADY_EXISTS");
        }
        Category category = new Category();
        category.setName(request.getCategoryName());
        Category save = categoryRepository.save(category);
        return new CategoryResponse(save.getName(), save.getId());
    }

    @Override
    public List<CategoryResponse> getAllCategory() {
        return categoryRepository.findAll()
                .stream()
                .map(c -> new CategoryResponse(c.getName(), c.getId()))
                .toList();
    }

    @Override
    public CategoryResponse updateCategory (Long id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        if (categoryRepository.existsByName(request.getCategoryName())) {
            throw new IllegalStateException("CATEGORY_ALREADY_EXISTS");
        }
        category.setName(request.getCategoryName());
        return new CategoryResponse(category.getName(), category.getId());
    }

    @Override
    public void deleteCategory (Long id) {
        categoryRepository.deleteById(id);
    }
}
