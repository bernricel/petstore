package com.musngi.petbrowsing.pets.application;

import com.musngi.petbrowsing.pets.api.CreatePetRequest;
import com.musngi.petbrowsing.pets.api.UpdatePetRequest;
import com.musngi.petbrowsing.pets.domain.PetRepository;
import com.musngi.petbrowsing.shared.ConflictException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class PetValidationService {

    private final PetRepository petRepository;
    private final Validator validator;

    public PetValidationService(PetRepository petRepository, Validator validator) {
        this.petRepository = petRepository;
        this.validator = validator;
    }

    public void validateCreateRequest(CreatePetRequest request) {
        if (petRepository.existsBySlugIgnoreCase(request.slug())) {
            throw new ConflictException("A pet with slug '%s' already exists.".formatted(request.slug()));
        }
    }

    public void validateUpdateRequest(UpdatePetRequest request, UUID petId) {
        Set<ConstraintViolation<CreatePetRequest>> violations = validator.validate(toCreateEquivalent(request));
        if (!violations.isEmpty()) {
            String message = violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining("; "));
            throw new IllegalArgumentException(message);
        }

        if (petRepository.existsBySlugIgnoreCaseAndIdNot(request.slug(), petId)) {
            throw new ConflictException("A pet with slug '%s' already exists.".formatted(request.slug()));
        }
    }

    private CreatePetRequest toCreateEquivalent(UpdatePetRequest request) {
        return new CreatePetRequest(
                request.categorySlug(),
                request.slug(),
                request.name(),
                request.breedOrType(),
                request.summary(),
                request.description(),
                request.priceAmount(),
                request.currencyCode(),
                request.availabilityStatus(),
                request.primaryImageUrl(),
                request.galleryImageUrls(),
                request.published()
        );
    }
}

