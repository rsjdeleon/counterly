package com.counterly.product.service;

import com.counterly.product.domain.Category;
import com.counterly.product.domain.Product;
import com.counterly.product.repositories.CategoryRepository;
import com.counterly.product.repositories.ProductRepository;
import com.counterly.product.web.mappers.ProductMapper;
import com.counterly.product.web.model.ProductDto;
import com.counterly.product.web.model.ProductPagedList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductPagedList listProduct(PageRequest pageRequest, Boolean active) {
        Page<Product> productPage = productRepository.findAllByActive(active, pageRequest);
        ProductPagedList productPagedList = new ProductPagedList(
                productPage
                        .getContent()
                        .stream()
                        .map(productMapper::productToProductDto)
                        .collect(Collectors.toList()),
                PageRequest.of(
                        productPage.getPageable().getPageNumber(),
                        productPage.getPageable().getPageSize()
                ),
                productPage.getTotalElements()
            );
        return productPagedList;
    }

    @Override
    public ProductDto findById(String id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isPresent()) {
            return productMapper.productToProductDto(productOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found. UUID: " + id);
        }
    }

    @Override
    public ProductDto saveProduct(ProductDto productDto) {
        Product product = productMapper.productDtoToProduct(productDto);
        if (productDto.getCategoryId() != null) {
            Optional<Category> categoryOptional = categoryRepository.findById(productDto.getCategoryId());
            categoryOptional.ifPresent(product::setCategory);
        }
        return productMapper.productToProductDto(
                productRepository.save(product)
        );
    }

    @Override
    public void updateProduct(String id, ProductDto productDto) {
        Optional<Product> productOptional = productRepository.findById(id);
        Optional<Category> categoryOptional = categoryRepository.findById(productDto.getCategoryId());

        productOptional.ifPresentOrElse(product -> {

            product.setName(productDto.getName());
            product.setManufacturerCode(productDto.getManufacturerCode());
            product.setDescription(productDto.getDescription());

            categoryOptional.ifPresent(product::setCategory);
            productRepository.save(product);
        }, ()->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found. UUID: " + id);
        });
    }

    @Override
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }
}
