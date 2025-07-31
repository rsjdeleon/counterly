package counterly.product.web.mappers;

import counterly.product.domain.Category;
import counterly.product.web.model.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class, componentModel = "spring")
public interface CategoryMapper {

    Category categoryDtoToCategory(CategoryDto categoryDto);

    CategoryDto categoryToCategoryDto(Category category);
}
