package com.uade.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.demo.controllers.YourCustomException;
import com.uade.demo.entity.Category;
import com.uade.demo.exceptions.CategoryDuplicateException;
import com.uade.demo.repository.CategoryRepository;

@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Page<Category> getCategories(PageRequest pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Optional<Category> getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId);
    }

    @Override
    public Optional<Category> getCategoryByDescription(String description) {
        return categoryRepository.findByDescription(description);
    }

    @Transactional(rollbackFor = Throwable.class)
    public Category createCategory(String description) throws CategoryDuplicateException {
        Optional<Category> categories = categoryRepository.findByDescription(description);
        if (categories.isEmpty())
            return categoryRepository.save(new Category(description));
        throw new YourCustomException("Categoria duplicada");
    }
}
