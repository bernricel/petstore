package com.musngi.petbrowsing.pets.api;

import com.musngi.petbrowsing.pets.domain.PetAvailabilityStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

public record CreatePetRequest(
        @NotBlank(message = "categorySlug is required.")
        String categorySlug,
        @NotBlank(message = "slug is required.")
        @Pattern(regexp = "^[a-z0-9]+(?:-[a-z0-9]+)*$", message = "slug must be URL-safe lowercase text.")
        String slug,
        @NotBlank(message = "name is required.")
        @Size(min = 2, max = 120, message = "name must be between 2 and 120 characters.")
        String name,
        @NotBlank(message = "breedOrType is required.")
        @Size(min = 2, max = 80, message = "breedOrType must be between 2 and 80 characters.")
        String breedOrType,
        @NotBlank(message = "summary is required.")
        @Size(min = 10, max = 255, message = "summary must be between 10 and 255 characters.")
        String summary,
        String description,
        @NotNull(message = "priceAmount is required.")
        @DecimalMin(value = "0.0", inclusive = true, message = "priceAmount must be zero or greater.")
        BigDecimal priceAmount,
        @NotBlank(message = "currencyCode is required.")
        @Pattern(regexp = "^[A-Z]{3}$", message = "currencyCode must be a 3-letter uppercase code.")
        String currencyCode,
        @NotNull(message = "availabilityStatus is required.")
        PetAvailabilityStatus availabilityStatus,
        String primaryImageUrl,
        List<String> galleryImageUrls,
        @NotNull(message = "published is required.")
        Boolean published
) {
}

