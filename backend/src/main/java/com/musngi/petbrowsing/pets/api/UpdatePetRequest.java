package com.musngi.petbrowsing.pets.api;

public record UpdatePetRequest(
        String categorySlug,
        String slug,
        String name,
        String breedOrType,
        String summary,
        String description,
        java.math.BigDecimal priceAmount,
        String currencyCode,
        com.musngi.petbrowsing.pets.domain.PetAvailabilityStatus availabilityStatus,
        String primaryImageUrl,
        java.util.List<String> galleryImageUrls,
        Boolean published
) {
}

