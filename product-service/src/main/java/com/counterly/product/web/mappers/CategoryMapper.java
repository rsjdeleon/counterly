package com.counterly.product.web.mappers;

import com.counterly.product.domain.Category;
import com.counterly.product.web.model.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class, componentModel = "spring")
public interface CategoryMapper {

    Category categoryDtoToCategory(CategoryDto categoryDto);

    CategoryDto categoryToCategoryDto(Category category);
}
