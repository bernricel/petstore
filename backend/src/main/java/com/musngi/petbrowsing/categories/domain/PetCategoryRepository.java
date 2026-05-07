package com.musngi.petbrowsing.categories.domain;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetCategoryRepository extends JpaRepository<PetCategory, UUID> {

    List<PetCategory> findByActiveTrueOrderBySortOrderAscDisplayNameAsc();

    Optional<PetCategory> findBySlugAndActiveTrue(String slug);
}

