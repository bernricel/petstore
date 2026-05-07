package com.musngi.petbrowsing.contract;

import com.musngi.petbrowsing.categories.api.CategoryResponse;
import com.musngi.petbrowsing.pets.api.AppliedFiltersResponse;
import com.musngi.petbrowsing.pets.api.PetDetailResponse;
import com.musngi.petbrowsing.pets.api.PetListResponse;
import com.musngi.petbrowsing.pets.api.PetSummaryResponse;
import com.musngi.petbrowsing.pets.application.PetCrudService;
import com.musngi.petbrowsing.pets.application.PetQueryService;
import com.musngi.petbrowsing.pets.domain.PetAvailabilityStatus;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.musngi.petbrowsing.pets.api.PetController;
import com.musngi.petbrowsing.shared.ApiExceptionHandler;

@WebMvcTest(PetController.class)
@Import(ApiExceptionHandler.class)
class PetDeleteAndReadContractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PetCrudService petCrudService;

    @MockBean
    private PetQueryService petQueryService;

    @Test
    void listPetsReturnsExpectedShape() throws Exception {
        PetSummaryResponse pet = new PetSummaryResponse(
                UUID.randomUUID(),
                "buddy",
                "Buddy",
                new CategoryResponse(UUID.randomUUID(), "dogs", "Dogs"),
                "Golden Retriever",
                "Friendly family dog ready for play.",
                new BigDecimal("1200.00"),
                "USD",
                PetAvailabilityStatus.AVAILABLE,
                null
        );
        given(petQueryService.listPublishedPets(null, null, com.musngi.petbrowsing.pets.domain.PetSort.FEATURED))
                .willReturn(new PetListResponse(List.of(pet), new AppliedFiltersResponse(null, null, "FEATURED")));

        mockMvc.perform(get("/api/musngi/catalog/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].slug").value("buddy"))
                .andExpect(jsonPath("$.appliedFilters.sort").value("FEATURED"));
    }

    @Test
    void deletePetReturnsNoContent() throws Exception {
        UUID petId = UUID.randomUUID();

        mockMvc.perform(delete("/api/musngi/catalog/pets/{petId}", petId))
                .andExpect(status().isNoContent());

        verify(petCrudService).deletePet(petId);
    }

    @Test
    void getPetStillReturnsDetailContract() throws Exception {
        UUID petId = UUID.randomUUID();
        given(petCrudService.getPet(petId)).willReturn(new PetDetailResponse(
                petId,
                "buddy",
                "Buddy",
                new CategoryResponse(UUID.randomUUID(), "dogs", "Dogs"),
                "Golden Retriever",
                "Friendly family dog ready for play.",
                new BigDecimal("1200.00"),
                "USD",
                PetAvailabilityStatus.AVAILABLE,
                null,
                "Buddy is a gentle Golden Retriever.",
                List.of(),
                true,
                OffsetDateTime.parse("2026-05-07T00:00:00Z")
        ));

        mockMvc.perform(get("/api/musngi/catalog/pets/{petId}", petId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.description").isNotEmpty());
    }
}

