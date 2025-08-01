package counterly.product.web.mappers;

import counterly.product.domain.Category;
import counterly.product.domain.Product;
import counterly.product.web.model.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = DateMapper.class, componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "category", source = "categoryId")
    Product productDtoToProduct(ProductDto productDto);

    @Mapping(target = "categoryId", source = "category.id")
    ProductDto productToProductDto(Product product);

    // Helper method to map String ID → Category entity
    default Category mapCategoryIdToCategory(String categoryId) {
        if (categoryId == null) return null;
        Category category = new Category();
        category.setId(categoryId); // Assuming Category.id is UUID
        return category;
    }

    // Helper method to map Category entity → String ID
    default String mapCategoryToCategoryId(Category category) {
        return category != null && category.getId() != null ? category.getId() : null;
    }
}
