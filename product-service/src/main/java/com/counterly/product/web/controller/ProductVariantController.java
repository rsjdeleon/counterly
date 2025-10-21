package com.counterly.product.web.controller;

import com.counterly.product.service.ProductVariantService;
import com.counterly.product.web.model.ProductVariantDto;
import com.counterly.product.web.model.ProductVariantPagedList;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/product-variant")
@RequiredArgsConstructor
@Slf4j
public class ProductVariantController {
    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;
    
    private final ProductVariantService productVariantService;

    @GetMapping
    public ResponseEntity<ProductVariantPagedList> listProduct(@RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                                                               @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                               @RequestParam(value = "active", required = false) Boolean active) {
        log.debug("Listing Products Variant");

        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        ProductVariantPagedList productPagedList = productVariantService.listProductVariant(
                PageRequest.of(pageNumber, pageSize),
                active
        );

        return ResponseEntity.ok(productPagedList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductVariantDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(productVariantService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody ProductVariantDto productVariantDto) {
        ProductVariantDto saved = productVariantService.saveProductVariant(productVariantDto);
        return ResponseEntity.created(URI.create("/api/v1/product/" + saved.getId())).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @Valid @RequestBody ProductVariantDto ProductDto) {
        productVariantService.updateProductVariant(id, ProductDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        productVariantService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

