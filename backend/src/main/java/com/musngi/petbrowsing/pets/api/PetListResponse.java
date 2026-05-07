package com.musngi.petbrowsing.pets.api;

import java.util.List;

public record PetListResponse(
        List<PetSummaryResponse> items,
        AppliedFiltersResponse appliedFilters
) {
}

