package com.musngi.petbrowsing.integration;

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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GalleryIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PetCategoryRepository categoryRepository;

    @Autowired
    private PetRepository petRepository;

    @BeforeEach
    void setUp() throws Exception {
        petRepository.deleteAll();
        categoryRepository.deleteAll();
        PetCategory dogs = new PetCategory();
        dogs.setSlug("dogs");
        dogs.setDisplayName("Dogs");
        dogs.setSortOrder(1);
        dogs.setActive(true);
        categoryRepository.save(dogs);

        PetCategory cats = new PetCategory();
        cats.setSlug("cats");
        cats.setDisplayName("Cats");
        cats.setSortOrder(2);
        cats.setActive(true);
        categoryRepository.save(cats);

        createPet("dogs", "buddy", PetAvailabilityStatus.AVAILABLE, true, new BigDecimal("1200.00"));
        createPet("cats", "luna", PetAvailabilityStatus.PENDING, true, new BigDecimal("900.00"));
        createPet("dogs", "hidden", PetAvailabilityStatus.AVAILABLE, false, new BigDecimal("700.00"));
    }

    @Test
    void categoryEndpointReturnsActiveCategories() throws Exception {
        mockMvc.perform(get("/api/musngi/catalog/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories.length()").value(2));
    }

    @Test
    void galleryEndpointFiltersPublishedPetsByCategoryAndSort() throws Exception {
        mockMvc.perform(get("/api/musngi/catalog/pets?category=dogs&sort=PRICE_ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items.length()").value(1))
                .andExpect(jsonPath("$.items[0].slug").value("buddy"));
    }

    private void createPet(
            String categorySlug,
            String slug,
            PetAvailabilityStatus availabilityStatus,
            boolean published,
            BigDecimal priceAmount
    ) throws Exception {
        CreatePetRequest request = new CreatePetRequest(
                categorySlug,
                slug,
                slug,
                "Breed",
                "A short gallery-ready summary.",
                "Longer description.",
                priceAmount,
                "USD",
                availabilityStatus,
                null,
                List.of(),
                published
        );

        mockMvc.perform(post("/api/musngi/catalog/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
