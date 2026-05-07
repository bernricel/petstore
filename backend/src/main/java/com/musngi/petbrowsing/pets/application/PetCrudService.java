package com.musngi.petbrowsing.pets.application;

import com.musngi.petbrowsing.categories.application.CategoryService;
import com.musngi.petbrowsing.categories.domain.PetCategory;
import com.musngi.petbrowsing.pets.api.CreatePetRequest;
import com.musngi.petbrowsing.pets.api.PetDetailResponse;
import com.musngi.petbrowsing.pets.api.UpdatePetRequest;
import com.musngi.petbrowsing.pets.domain.Pet;
import com.musngi.petbrowsing.pets.domain.PetRepository;
import com.musngi.petbrowsing.shared.NotFoundException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PetCrudService {

    private final PetRepository petRepository;
    private final CategoryService categoryService;
    private final PetValidationService petValidationService;
    private final PetMapper petMapper;

    public PetCrudService(
            PetRepository petRepository,
            CategoryService categoryService,
            PetValidationService petValidationService,
            PetMapper petMapper
    ) {
        this.petRepository = petRepository;
        this.categoryService = categoryService;
        this.petValidationService = petValidationService;
        this.petMapper = petMapper;
    }

    @Transactional
    public PetDetailResponse createPet(CreatePetRequest request) {
        petValidationService.validateCreateRequest(request);
        PetCategory category = categoryService.requireActiveCategory(request.categorySlug());
        Pet pet = new Pet();
        applyCommonFields(pet, category, request);
        return petMapper.toDetail(petRepository.save(pet));
    }

    @Transactional
    public PetDetailResponse getPet(UUID petId) {
        return petMapper.toDetail(getExistingPet(petId));
    }

    @Transactional
    public PetDetailResponse updatePet(UUID petId, UpdatePetRequest request) {
        petValidationService.validateUpdateRequest(request, petId);
        Pet existing = getExistingPet(petId);
        PetCategory category = categoryService.requireActiveCategory(request.categorySlug());
        applyCommonFields(existing, category, request);
        return petMapper.toDetail(petRepository.save(existing));
    }

    @Transactional
    public void deletePet(UUID petId) {
        Pet pet = getExistingPet(petId);
        petRepository.delete(pet);
    }

    private Pet getExistingPet(UUID petId) {
        return petRepository.findByIdAndPublishedTrue(petId)
                .orElseThrow(() -> new NotFoundException("Pet '%s' was not found.".formatted(petId)));
    }

    private void applyCommonFields(Pet pet, PetCategory category, CreatePetRequest request) {
        pet.setCategory(category);
        pet.setSlug(request.slug());
        pet.setName(request.name());
        pet.setBreedOrType(request.breedOrType());
        pet.setSummary(request.summary());
        pet.setDescription(Optional.ofNullable(request.description()).orElse(""));
        pet.setPriceAmount(request.priceAmount());
        pet.setCurrencyCode(request.currencyCode());
        pet.setAvailabilityStatus(request.availabilityStatus());
        pet.setPrimaryImageUrl(request.primaryImageUrl());
        pet.setGalleryImageUrls(request.galleryImageUrls());
        pet.setPublished(Boolean.TRUE.equals(request.published()));
    }

    private void applyCommonFields(Pet pet, PetCategory category, UpdatePetRequest request) {
        pet.setCategory(category);
        pet.setSlug(request.slug());
        pet.setName(request.name());
        pet.setBreedOrType(request.breedOrType());
        pet.setSummary(request.summary());
        pet.setDescription(Optional.ofNullable(request.description()).orElse(""));
        pet.setPriceAmount(request.priceAmount());
        pet.setCurrencyCode(request.currencyCode());
        pet.setAvailabilityStatus(request.availabilityStatus());
        pet.setPrimaryImageUrl(request.primaryImageUrl());
        pet.setGalleryImageUrls(request.galleryImageUrls());
        pet.setPublished(Boolean.TRUE.equals(request.published()));
    }
}
