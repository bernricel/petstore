package com.musngi.petbrowsing.categories.api;

import com.musngi.petbrowsing.categories.application.CategoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/musngi/catalog/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public CategoryListResponse listCategories() {
        return new CategoryListResponse(categoryService.listActiveCategories());
    }
}

