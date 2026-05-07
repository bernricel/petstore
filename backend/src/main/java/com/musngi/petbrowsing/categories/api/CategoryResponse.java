package com.musngi.petbrowsing.categories.api;

import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String slug,
        String displayName
) {
}

