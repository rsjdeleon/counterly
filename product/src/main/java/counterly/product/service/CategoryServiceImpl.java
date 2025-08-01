package counterly.product.service;

import counterly.product.domain.Category;
import counterly.product.repositories.CategoryRepository;
import counterly.product.web.mappers.CategoryMapper;
import counterly.product.web.model.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> listCategory(Boolean active) {
        return categoryRepository.findAllByActive(active).stream().map(categoryMapper::categoryToCategoryDto).toList();
    }

    @Override
    public CategoryDto findById(String id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        if (categoryOptional.isPresent()) {
            return categoryMapper.categoryToCategoryDto(categoryOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found. UUID: " + id);
        }
    }

    @Override
    public CategoryDto saveCategory(CategoryDto categoryDto) {
        return categoryMapper.categoryToCategoryDto(
                categoryRepository.save(
                        categoryMapper.categoryDtoToCategory(categoryDto)
                )
        );
    }

    @Override
    public void updateCategory(String id, CategoryDto categoryDto) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);

        categoryOptional.ifPresentOrElse(category -> {
            category.setName(categoryDto.getName());
            categoryRepository.save(category);
        }, ()->{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found. UUID: " + id);
        });
    }

    @Override
    public void deleteById(String id) {
        categoryRepository.deleteById(id);
    }
}
