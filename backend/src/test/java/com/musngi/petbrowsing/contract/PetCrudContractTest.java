package com.musngi.petbrowsing.contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musngi.petbrowsing.categories.api.CategoryResponse;
import com.musngi.petbrowsing.pets.api.CreatePetRequest;
import com.musngi.petbrowsing.pets.api.PetDetailResponse;
import com.musngi.petbrowsing.pets.api.UpdatePetRequest;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.musngi.petbrowsing.pets.api.PetController;
import com.musngi.petbrowsing.shared.ApiExceptionHandler;

@WebMvcTest(PetController.class)
@Import(ApiExceptionHandler.class)
class PetCrudContractTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PetCrudService petCrudService;

    @MockBean
    private PetQueryService petQueryService;

    @Test
    void createPetReturnsCreatedContract() throws Exception {
        UUID petId = UUID.randomUUID();
        given(petCrudService.createPet(any(CreatePetRequest.class))).willReturn(sampleDetail(petId, "buddy"));

        CreatePetRequest request = new CreatePetRequest(
                "dogs",
                "buddy",
                "Buddy",
                "Golden Retriever",
                "Friendly family dog ready for play.",
                "Buddy is a gentle Golden Retriever.",
                new BigDecimal("1200.00"),
                "USD",
                PetAvailabilityStatus.AVAILABLE,
                null,
                List.of(),
                true
        );

        mockMvc.perform(post("/api/musngi/catalog/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(petId.toString()))
                .andExpect(jsonPath("$.slug").value("buddy"))
                .andExpect(jsonPath("$.category.slug").value("dogs"))
                .andExpect(jsonPath("$.published").value(true));
    }

    @Test
    void getPetReturnsDetailContract() throws Exception {
        UUID petId = UUID.randomUUID();
        given(petCrudService.getPet(petId)).willReturn(sampleDetail(petId, "buddy"));

        mockMvc.perform(get("/api/musngi/catalog/pets/{petId}", petId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(petId.toString()))
                .andExpect(jsonPath("$.name").value("Buddy"))
                .andExpect(jsonPath("$.availabilityStatus").value("AVAILABLE"));
    }

    @Test
    void updatePetReturnsUpdatedContract() throws Exception {
        UUID petId = UUID.randomUUID();
        UpdatePetRequest request = new UpdatePetRequest(
                "dogs",
                "buddy",
                "Buddy Updated",
                "Golden Retriever",
                "Updated summary text.",
                "Updated description.",
                new BigDecimal("1300.00"),
                "USD",
                PetAvailabilityStatus.PENDING,
                null,
                List.of(),
                true
        );

        given(petCrudService.updatePet(eq(petId), any(UpdatePetRequest.class)))
                .willReturn(sampleDetail(petId, "buddy"));

        mockMvc.perform(put("/api/musngi/catalog/pets/{petId}", petId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(petId.toString()))
                .andExpect(jsonPath("$.slug").value("buddy"));
    }

    private PetDetailResponse sampleDetail(UUID petId, String slug) {
        return new PetDetailResponse(
                petId,
                slug,
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
        );
    }
}

