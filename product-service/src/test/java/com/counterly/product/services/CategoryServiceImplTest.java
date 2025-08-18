package com.counterly.product.services;

import com.counterly.product.domain.Category;
import com.counterly.product.repositories.CategoryRepository;
import com.counterly.product.service.CategoryServiceImpl;
import com.counterly.product.web.mappers.CategoryMapper;
import com.counterly.product.web.model.CategoryDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Find By UUID")
    void testFindById() {
        UUID id = UUID.randomUUID();

        // Domain object
        Category category = new Category();
        category.setId(id.toString());
        category.setName("Test Category");

        // DTO object
        CategoryDto categoryDto = CategoryDto.builder()
                .id(id.toString())
                .name("Test Category")
                .build();

        // Mock behavior
        when(categoryRepository.findById(id.toString())).thenReturn(Optional.of(category));
        when(categoryMapper.categoryToCategoryDto(category)).thenReturn(categoryDto);

        // Call method
        CategoryDto result = categoryService.findById(id.toString());

        // Verify result
        assertEquals("Test Category", result.getName());
        assertEquals(id.toString(), result.getId());
    }

    @Test
    @DisplayName("Find All Active Category")
    public void findAllActiveCategory() {
        Category category = new Category();
        CategoryDto categoryDto = new CategoryDto();

        when(categoryRepository.findAllByActive(true)).thenReturn(List.of(category));
        when(categoryMapper.categoryToCategoryDto(category)).thenReturn(categoryDto);

        List<CategoryDto> result = categoryService.listCategory(true);

        // THEN
        assertEquals(1, result.size());
        Mockito.verify(categoryRepository).findAllByActive(true);
    }

    @Test
    @DisplayName("Find All Inactive Category")
    public void findAllInactiveCategory() {
        Category category = new Category();
        CategoryDto categoryDto = new CategoryDto();

        when(categoryRepository.findAllByActive(false)).thenReturn(List.of(category));
        when(categoryMapper.categoryToCategoryDto(category)).thenReturn(categoryDto);

        List<CategoryDto> result = categoryService.listCategory(false);

        // THEN
        assertEquals(1, result.size());
        Mockito.verify(categoryRepository).findAllByActive(false);
    }
}
