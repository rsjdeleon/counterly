package counterly.product.service;

import counterly.product.web.model.ProductVariantDto;
import counterly.product.web.model.ProductVariantPagedList;
import org.springframework.data.domain.PageRequest;

public interface ProductVariantService {

    ProductVariantPagedList listProductVariant(PageRequest pageRequest, Boolean active);

    ProductVariantDto findById(String id);

    ProductVariantDto saveProductVariant(ProductVariantDto productDto);

    void updateProductVariant(String id, ProductVariantDto productDto);

    void deleteById(String id);

}
