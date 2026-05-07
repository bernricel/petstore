package com.musngi.petbrowsing.pets.api;

public record AppliedFiltersResponse(
        String category,
        String availability,
        String sort
) {
}

