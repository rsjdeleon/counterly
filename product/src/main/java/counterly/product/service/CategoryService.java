package counterly.product.service;

import counterly.product.web.model.CategoryDto;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryDto> listCategory(Boolean active);

    CategoryDto findById(String id);

    CategoryDto saveCategory(CategoryDto categoryDto);

    void updateCategory(String id, CategoryDto categoryDto);

    void deleteById(String id);

}
