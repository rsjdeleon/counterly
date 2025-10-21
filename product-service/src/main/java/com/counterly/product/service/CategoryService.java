package com.counterly.product.service;

import com.counterly.product.web.model.CategoryDto;

import java.util.List;

public interface CategoryService {

    List<CategoryDto> listCategory(Boolean active);

    CategoryDto findById(String id);

    CategoryDto saveCategory(CategoryDto categoryDto);

    void updateCategory(String id, CategoryDto categoryDto);

    void deleteById(String id);

}
