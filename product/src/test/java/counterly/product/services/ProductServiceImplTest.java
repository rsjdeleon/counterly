package counterly.product.services;

import counterly.product.domain.Category;
import counterly.product.domain.Product;
import counterly.product.repositories.CategoryRepository;
import counterly.product.repositories.ProductRepository;
import counterly.product.service.CategoryServiceImpl;
import counterly.product.service.ProductServiceImpl;
import counterly.product.web.mappers.CategoryMapper;
import counterly.product.web.mappers.ProductMapper;
import counterly.product.web.model.CategoryDto;
import counterly.product.web.model.ProductDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    @DisplayName("Find By UUID")
    void testFindById() {
        UUID id = UUID.randomUUID();

        // Domain object
        Product product = Product.builder()
                .id(id.toString())
                .name("Test Product")
                .build();

        // DTO object
        ProductDto productDto = ProductDto.builder()
                .id(id.toString())
                .name("Test Product")
                .build();

        // Mock behavior
        when(productRepository.findById(id.toString())).thenReturn(Optional.of(product));
        when(productMapper.productToProductDto(product)).thenReturn(productDto);

        // Call method
        ProductDto result = productService.findById(id.toString());

        // Verify result
        assertEquals("Test Product", result.getName());
        assertEquals(id.toString(), result.getId());
    }

}
