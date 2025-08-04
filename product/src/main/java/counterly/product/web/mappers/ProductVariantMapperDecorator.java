package counterly.product.web.mappers;

import counterly.product.domain.ProductVariant;
import counterly.product.service.ProductService;
import counterly.product.web.model.ProductDto;
import counterly.product.web.model.ProductVariantDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Created by Raymond on 2025-08-04.
 */
public abstract class ProductVariantMapperDecorator implements ProductVariantMapper {
    private ProductService productService;
    private ProductVariantMapper mapper;

    @Autowired
    public void setProductVariantService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    @Qualifier("delegate")
    public void setMapper(ProductVariantMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ProductVariantDto productVariantToProductVariantDto(ProductVariant productVariant) {
        ProductVariantDto dto = mapper.productVariantToProductVariantDto(productVariant);

        ProductDto product = productService.findById(productVariant.getProduct().getId());
        if (product != null) {
            dto.setProductId(product.getId());
            dto.setName(product.getName());
            dto.setManufacturerCode(product.getManufacturerCode());
            dto.setDescription(product.getDescription());
        }

        return dto;
    }
}
