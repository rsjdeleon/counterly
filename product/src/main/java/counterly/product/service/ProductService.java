package counterly.product.service;

import counterly.product.web.model.CategoryDto;
import counterly.product.web.model.ProductDto;
import counterly.product.web.model.ProductPagedList;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    ProductPagedList listProduct(PageRequest pageRequest, Boolean active);

    ProductDto findById(String id);

    ProductDto saveProduct(ProductDto productDto);

    void updateProduct(String id, ProductDto productDto);

    void deleteById(String id);

}
