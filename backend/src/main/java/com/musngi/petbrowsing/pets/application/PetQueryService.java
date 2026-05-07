package com.musngi.petbrowsing.pets.application;

import com.musngi.petbrowsing.categories.application.CategoryService;
import com.musngi.petbrowsing.pets.api.AppliedFiltersResponse;
import com.musngi.petbrowsing.pets.api.PetListResponse;
import com.musngi.petbrowsing.pets.domain.Pet;
import com.musngi.petbrowsing.pets.domain.PetAvailabilityStatus;
import com.musngi.petbrowsing.pets.domain.PetRepository;
import com.musngi.petbrowsing.pets.domain.PetSort;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class PetQueryService {

    private final PetRepository petRepository;
    private final PetMapper petMapper;
    private final CategoryService categoryService;

    public PetQueryService(PetRepository petRepository, PetMapper petMapper, CategoryService categoryService) {
        this.petRepository = petRepository;
        this.petMapper = petMapper;
        this.categoryService = categoryService;
    }

    @Transactional
    public PetListResponse listPublishedPets(String categorySlug, PetAvailabilityStatus availability, PetSort sort) {
        if (categorySlug != null && !categorySlug.isBlank()) {
            categoryService.requireActiveCategory(categorySlug);
        }

        List<Pet> pets = petRepository.findAll(byPublishedFilters(categorySlug, availability), toSort(sort));
        return new PetListResponse(
                pets.stream().map(petMapper::toSummary).toList(),
                new AppliedFiltersResponse(categorySlug, availability == null ? null : availability.name(), sort.name())
        );
    }

    private Specification<Pet> byPublishedFilters(String categorySlug, PetAvailabilityStatus availability) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(criteriaBuilder.isTrue(root.get("published")));

            if (categorySlug != null && !categorySlug.isBlank()) {
                predicates.add(criteriaBuilder.equal(root.join("category").get("slug"), categorySlug));
            }

            if (availability != null) {
                predicates.add(criteriaBuilder.equal(root.get("availabilityStatus"), availability));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Sort toSort(PetSort sort) {
        return switch (sort) {
            case PRICE_ASC -> Sort.by(Sort.Direction.ASC, "priceAmount");
            case PRICE_DESC -> Sort.by(Sort.Direction.DESC, "priceAmount");
            case NAME_ASC -> Sort.by(Sort.Direction.ASC, "name");
            case NEWEST -> Sort.by(Sort.Direction.DESC, "createdAt");
            case FEATURED -> Sort.by(Sort.Direction.ASC, "category.sortOrder").and(Sort.by("name"));
        };
    }
}
