package counterly.product.service;

import counterly.product.web.model.CategoryDto;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface CategoryService {

    List<CategoryDto> listCategory(Boolean active);

    CategoryDto findById(UUID id);

    CategoryDto saveCategory(CategoryDto beerDto);

    void updateCategory(UUID id, CategoryDto beerDto);

    void deleteById(UUID id);

}
