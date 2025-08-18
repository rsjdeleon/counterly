package com.counterly.product.web.controller;

import com.counterly.product.service.ProductService;
import com.counterly.product.web.model.ProductDto;
import com.counterly.product.web.model.ProductPagedList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;
    
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<ProductPagedList> listProduct(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                        @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                        @RequestParam(value = "active", required = false) Boolean active) {
        log.debug("Listing Products");

        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        ProductPagedList productPagedList = productService.listProduct(
                PageRequest.of(pageNumber, pageSize),
                active
        );

        return ResponseEntity.ok(productPagedList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ProductDto ProductDto) {
        ProductDto saved = productService.saveProduct(ProductDto);
        return ResponseEntity.created(URI.create("/api/v1/product/" + saved.getId())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @Valid @RequestBody ProductDto ProductDto) {
        productService.updateProduct(id, ProductDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

