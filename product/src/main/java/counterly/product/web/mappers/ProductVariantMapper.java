package counterly.product.web.mappers;

import counterly.product.domain.Category;
import counterly.product.domain.Product;
import counterly.product.domain.ProductVariant;
import counterly.product.web.model.ProductDto;
import counterly.product.web.model.ProductVariantDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = DateMapper.class, componentModel = "spring")
@DecoratedWith(ProductVariantMapperDecorator.class)
public interface ProductVariantMapper {

    @Mapping(target = "product", source = "productId")
    ProductVariant productVariantDtoToProductVariant(ProductVariantDto productVariantDto);

    @Mapping(target = "productId", source = "product.id")
    ProductVariantDto productVariantToProductVariantDto(ProductVariant productVariant);

    // Helper method to map String ID → product entity
    default Product mapProductIdToProduct(String productId) {
        if (productId == null) return null;
        Product product = new Product();
        product.setId(productId); // Assuming Category.id is UUID
        return product;
    }

    // Helper method to map product  entity → String ID
    default String mapProductToProductId(Product product) {
        return product != null && product.getId() != null ? product.getId() : null;
    }
}
