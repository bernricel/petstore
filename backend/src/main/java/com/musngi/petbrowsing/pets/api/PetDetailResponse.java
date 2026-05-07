package com.musngi.petbrowsing.pets.api;

import com.musngi.petbrowsing.categories.api.CategoryResponse;
import com.musngi.petbrowsing.pets.domain.PetAvailabilityStatus;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public record PetDetailResponse(
        UUID id,
        String slug,
        String name,
        CategoryResponse category,
        String breedOrType,
        String summary,
        BigDecimal priceAmount,
        String currencyCode,
        PetAvailabilityStatus availabilityStatus,
        String primaryImageUrl,
        String description,
        List<String> galleryImageUrls,
        boolean published,
        OffsetDateTime lastUpdatedAt
) {
}

