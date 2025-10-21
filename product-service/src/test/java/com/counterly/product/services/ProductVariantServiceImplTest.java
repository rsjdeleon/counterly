package com.counterly.product.services;

import com.counterly.product.domain.ProductVariant;
import com.counterly.product.repositories.ProductVariantRepository;
import com.counterly.product.service.ProductVariantServiceImpl;
import com.counterly.product.web.mappers.ProductVariantMapper;
import com.counterly.product.web.model.ProductVariantDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductVariantServiceImplTest {
    @Mock
    private ProductVariantRepository productVariantRepository;

    @Mock
    private ProductVariantMapper productVariantMapper;

    @InjectMocks
    private ProductVariantServiceImpl productVariantService;

    @Test
    @DisplayName("Find By UUID")
    void testFindById() {
        UUID id = UUID.randomUUID();

        // Domain object
        ProductVariant product = ProductVariant.builder()
                .id(id.toString())
                .sku("Test Product Variant")
                .build();

        // DTO object
        ProductVariantDto productDto = ProductVariantDto.builder()
                .id(id.toString())
                .sku("Test Product Variant")
                .build();

        // Mock behavior
        when(productVariantRepository.findById(id.toString())).thenReturn(Optional.of(product));
        when(productVariantMapper.productVariantToProductVariantDto(product)).thenReturn(productDto);

        // Call method
        ProductVariantDto result = productVariantService.findById(id.toString());

        // Verify result
        assertEquals("Test Product Variant", result.getSku());
        assertEquals(id.toString(), result.getId());
    }

}
