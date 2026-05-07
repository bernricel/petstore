package com.musngi.petbrowsing.pets.api;

import com.musngi.petbrowsing.pets.application.PetCrudService;
import com.musngi.petbrowsing.pets.application.PetQueryService;
import com.musngi.petbrowsing.pets.domain.PetAvailabilityStatus;
import com.musngi.petbrowsing.pets.domain.PetSort;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/musngi/catalog/pets")
public class PetController {

    private final PetCrudService petCrudService;
    private final PetQueryService petQueryService;

    public PetController(PetCrudService petCrudService, PetQueryService petQueryService) {
        this.petCrudService = petCrudService;
        this.petQueryService = petQueryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PetDetailResponse createPet(@Valid @RequestBody CreatePetRequest request) {
        return petCrudService.createPet(request);
    }

    @GetMapping
    public PetListResponse listPets(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) PetAvailabilityStatus availability,
            @RequestParam(defaultValue = "FEATURED") PetSort sort
    ) {
        return petQueryService.listPublishedPets(category, availability, sort);
    }

    @GetMapping("/{petId}")
    public PetDetailResponse getPet(@PathVariable @NotNull UUID petId) {
        return petCrudService.getPet(petId);
    }

    @PutMapping("/{petId}")
    public PetDetailResponse updatePet(@PathVariable UUID petId, @RequestBody UpdatePetRequest request) {
        return petCrudService.updatePet(petId, request);
    }

    @DeleteMapping("/{petId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePet(@PathVariable UUID petId) {
        petCrudService.deletePet(petId);
    }
}

