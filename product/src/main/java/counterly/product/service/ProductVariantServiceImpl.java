package counterly.product.service;

import counterly.product.domain.Category;
import counterly.product.domain.Product;
import counterly.product.domain.ProductVariant;
import counterly.product.repositories.CategoryRepository;
import counterly.product.repositories.ProductRepository;
import counterly.product.repositories.ProductVariantRepository;
import counterly.product.web.mappers.ProductMapper;
import counterly.product.web.mappers.ProductVariantMapper;
import counterly.product.web.model.ProductDto;
import counterly.product.web.model.ProductPagedList;
import counterly.product.web.model.ProductVariantDto;
import counterly.product.web.model.ProductVariantPagedList;
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
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;
    private final ProductVariantMapper productVariantMapper;

    @Override
    public ProductVariantPagedList listProductVariant(PageRequest pageRequest, Boolean active) {
        Page<ProductVariant> productPage = productVariantRepository.findAllByActive(active, pageRequest);
        ProductVariantPagedList productVariantPagedList = new ProductVariantPagedList(
                productPage
                        .getContent()
                        .stream()
                        .map(productVariantMapper::productVariantToProductVariantDto)
                        .collect(Collectors.toList()),
                PageRequest.of(
                        productPage.getPageable().getPageNumber(),
                        productPage.getPageable().getPageSize()
                ),
                productPage.getTotalElements()
        );
        return productVariantPagedList;
    }

    @Override
    public ProductVariantDto findById(String id) {
        Optional<ProductVariant> productOptional = productVariantRepository.findById(id);

        if (productOptional.isPresent()) {
            return productVariantMapper.productVariantToProductVariantDto(productOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found. UUID: " + id);
        }
    }

    @Override
    public ProductVariantDto saveProductVariant(ProductVariantDto productVariantDto) {
        ProductVariant product = productVariantMapper.productVariantDtoToProductVariant(productVariantDto);
        if (productVariantDto.getProductId() != null) {
            Optional<Product> productOptional = productRepository.findById(productVariantDto.getProductId());
            if (productOptional.isPresent()) {
                product.setProduct(productOptional.get());
            }
        }
        return productVariantMapper.productVariantToProductVariantDto(
                productVariantRepository.save(product)
        );
    }

    @Override
    public void updateProductVariant(String id, ProductVariantDto productVariantDto) {
        Optional<ProductVariant> productVariantOptional = productVariantRepository.findById(id);
        Optional<Product> productOptional = productRepository.findById(productVariantDto.getProductId());

        productVariantOptional.ifPresentOrElse(productVariant -> {

            productVariant.setSku(productVariantDto.getSku());
            productVariant.setBarcode(productVariantDto.getBarcode());
            productVariant.setAttributes(productVariantDto.getAttributes());
            productVariant.setPrice(productVariantDto.getPrice());

            productOptional.ifPresent(productVariant::setProduct);

            productVariantRepository.save(productVariant);
        }, ()->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found. UUID: " + id);
        });
    }


    @Override
    public void deleteById(String id) {
        productRepository.deleteById(id);
    }
}
