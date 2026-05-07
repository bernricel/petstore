package com.musngi.petbrowsing.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musngi.petbrowsing.categories.domain.PetCategory;
import com.musngi.petbrowsing.categories.domain.PetCategoryRepository;
import com.musngi.petbrowsing.pets.api.CreatePetRequest;
import com.musngi.petbrowsing.pets.api.UpdatePetRequest;
import com.musngi.petbrowsing.pets.domain.PetAvailabilityStatus;
import com.musngi.petbrowsing.pets.domain.PetRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PetCrudIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PetCategoryRepository categoryRepository;

    @Autowired
    private PetRepository petRepository;

    @BeforeEach
    void setUp() {
        petRepository.deleteAll();
        categoryRepository.deleteAll();
        PetCategory category = new PetCategory();
        category.setSlug("dogs");
        category.setDisplayName("Dogs");
        category.setSortOrder(1);
        category.setActive(true);
        categoryRepository.save(category);
    }

    @Test
    void createReadAndUpdatePetFlowWorks() throws Exception {
        CreatePetRequest createRequest = new CreatePetRequest(
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

        String response = mockMvc.perform(post("/api/musngi/catalog/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.slug").value("buddy"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String petId = objectMapper.readTree(response).get("id").asText();

        mockMvc.perform(get("/api/musngi/catalog/pets/{petId}", petId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy"));

        UpdatePetRequest updateRequest = new UpdatePetRequest(
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

        mockMvc.perform(put("/api/musngi/catalog/pets/{petId}", petId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Buddy Updated"))
                .andExpect(jsonPath("$.availabilityStatus").value("PENDING"));
    }

    @Test
    void duplicateSlugIsRejected() throws Exception {
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
                .andExpect(status().isCreated());

        mockMvc.perform(post("/api/musngi/catalog/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    void invalidPayloadIsRejectedWithoutAuthentication() throws Exception {
        CreatePetRequest request = new CreatePetRequest(
                "dogs",
                "not valid slug",
                "",
                "",
                "short",
                "",
                new BigDecimal("-1.00"),
                "usd",
                null,
                null,
                List.of(),
                true
        );

        mockMvc.perform(post("/api/musngi/catalog/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("VALIDATION_ERROR"));
    }
}
