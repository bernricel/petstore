package com.musngi.petbrowsing.pets.application;

import com.musngi.petbrowsing.categories.api.CategoryResponse;
import com.musngi.petbrowsing.categories.domain.PetCategory;
import com.musngi.petbrowsing.pets.api.PetDetailResponse;
import com.musngi.petbrowsing.pets.api.PetSummaryResponse;
import com.musngi.petbrowsing.pets.domain.Pet;
import org.springframework.stereotype.Component;

@Component
public class PetMapper {

    public PetSummaryResponse toSummary(Pet pet) {
        return new PetSummaryResponse(
                pet.getId(),
                pet.getSlug(),
                pet.getName(),
                toCategoryResponse(pet.getCategory()),
                pet.getBreedOrType(),
                pet.getSummary(),
                pet.getPriceAmount(),
                pet.getCurrencyCode(),
                pet.getAvailabilityStatus(),
                pet.getPrimaryImageUrl()
        );
    }

    public PetDetailResponse toDetail(Pet pet) {
        return new PetDetailResponse(
                pet.getId(),
                pet.getSlug(),
                pet.getName(),
                toCategoryResponse(pet.getCategory()),
                pet.getBreedOrType(),
                pet.getSummary(),
                pet.getPriceAmount(),
                pet.getCurrencyCode(),
                pet.getAvailabilityStatus(),
                pet.getPrimaryImageUrl(),
                pet.getDescription(),
                pet.getGalleryImageUrls(),
                pet.isPublished(),
                pet.getUpdatedAt()
        );
    }

    private CategoryResponse toCategoryResponse(PetCategory category) {
        return new CategoryResponse(category.getId(), category.getSlug(), category.getDisplayName());
    }
}

