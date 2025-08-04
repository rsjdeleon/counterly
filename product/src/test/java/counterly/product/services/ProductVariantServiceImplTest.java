package counterly.product.services;

import counterly.product.domain.Product;
import counterly.product.domain.ProductVariant;
import counterly.product.repositories.ProductRepository;
import counterly.product.repositories.ProductVariantRepository;
import counterly.product.service.ProductServiceImpl;
import counterly.product.service.ProductVariantService;
import counterly.product.service.ProductVariantServiceImpl;
import counterly.product.web.mappers.ProductMapper;
import counterly.product.web.mappers.ProductVariantMapper;
import counterly.product.web.model.ProductDto;
import counterly.product.web.model.ProductVariantDto;
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
