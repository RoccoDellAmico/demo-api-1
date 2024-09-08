package com.uade.demo.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.demo.entity.Category;
import com.uade.demo.exceptions.CategoryDuplicateException;


public interface CategoryService {
    public Page<Category> getCategories(PageRequest pageRequest);

    public Optional<Category> getCategoryById(Long categoryId);

    public Optional<Category> getCategoryByDescription(String description);

    public Category createCategory(String description) throws CategoryDuplicateException;
}
