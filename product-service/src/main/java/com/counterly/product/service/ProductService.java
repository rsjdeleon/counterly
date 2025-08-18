package com.counterly.product.service;

import com.counterly.product.web.model.ProductDto;
import com.counterly.product.web.model.ProductPagedList;
import org.springframework.data.domain.PageRequest;

public interface ProductService {

    ProductPagedList listProduct(PageRequest pageRequest, Boolean active);

    ProductDto findById(String id);

    ProductDto saveProduct(ProductDto productDto);

    void updateProduct(String id, ProductDto productDto);

    void deleteById(String id);

}
