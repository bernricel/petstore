package com.musngi.petbrowsing.categories.application;

import com.musngi.petbrowsing.categories.api.CategoryResponse;
import com.musngi.petbrowsing.categories.domain.PetCategory;
import com.musngi.petbrowsing.categories.domain.PetCategoryRepository;
import com.musngi.petbrowsing.shared.NotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final PetCategoryRepository categoryRepository;

    public CategoryService(PetCategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryResponse> listActiveCategories() {
        return categoryRepository.findByActiveTrueOrderBySortOrderAscDisplayNameAsc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public PetCategory requireActiveCategory(String slug) {
        return categoryRepository.findBySlugAndActiveTrue(slug)
                .orElseThrow(() -> new NotFoundException("Category '%s' was not found.".formatted(slug)));
    }

    private CategoryResponse toResponse(PetCategory category) {
        return new CategoryResponse(category.getId(), category.getSlug(), category.getDisplayName());
    }
}

