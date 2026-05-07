package com.musngi.petbrowsing.integration;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.musngi.petbrowsing.categories.domain.PetCategory;
import com.musngi.petbrowsing.categories.domain.PetCategoryRepository;
import com.musngi.petbrowsing.pets.api.CreatePetRequest;
import com.musngi.petbrowsing.pets.domain.PetAvailabilityStatus;
import com.musngi.petbrowsing.pets.domain.PetRepository;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PetDeleteAndReadIntegrationTest {

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
    void listAndDeleteFlowWorks() throws Exception {
        String petId = createPet("buddy");

        mockMvc.perform(get("/api/musngi/catalog/pets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].id").value(petId));

        mockMvc.perform(delete("/api/musngi/catalog/pets/{petId}", petId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/musngi/catalog/pets/{petId}", petId))
                .andExpect(status().isNotFound());
    }

    @Test
    void deletingUnknownPetReturnsNotFound() throws Exception {
        mockMvc.perform(delete("/api/musngi/catalog/pets/{petId}", "5a5af8d7-e095-4f44-bde4-797a672d3fe3"))
                .andExpect(status().isNotFound());
    }

    private String createPet(String slug) throws Exception {
        CreatePetRequest request = new CreatePetRequest(
                "dogs",
                slug,
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
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(response);
        return jsonNode.get("id").asText();
    }
}
