package com.musngi.petbrowsing.contract;

import com.musngi.petbrowsing.categories.api.CategoryController;
import com.musngi.petbrowsing.categories.api.CategoryListResponse;
import com.musngi.petbrowsing.categories.api.CategoryResponse;
import com.musngi.petbrowsing.categories.application.CategoryService;
import com.musngi.petbrowsing.pets.api.AppliedFiltersResponse;
import com.musngi.petbrowsing.pets.api.PetListResponse;
import com.musngi.petbrowsing.pets.api.PetSummaryResponse;
import com.musngi.petbrowsing.pets.application.PetCrudService;
import com.musngi.petbrowsing.pets.application.PetQueryService;
import com.musngi.petbrowsing.pets.domain.PetAvailabilityStatus;
import com.musngi.petbrowsing.pets.domain.PetSort;
import com.musngi.petbrowsing.shared.ApiExceptionHandler;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({CategoryController.class, com.musngi.petbrowsing.pets.api.PetController.class})
@Import(ApiExceptionHandler.class)
class GalleryContractTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private PetCrudService petCrudService;

    @MockBean
    private PetQueryService petQueryService;

    @Test
    void listCategoriesReturnsExpectedContract() throws Exception {
        given(categoryService.listActiveCategories()).willReturn(List.of(
                new CategoryResponse(UUID.randomUUID(), "dogs", "Dogs")
        ));

        mockMvc.perform(get("/api/musngi/catalog/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories[0].slug").value("dogs"));
    }

    @Test
    void listGallerySupportsFiltersContract() throws Exception {
        given(petQueryService.listPublishedPets("dogs", PetAvailabilityStatus.AVAILABLE, PetSort.PRICE_ASC))
                .willReturn(new PetListResponse(
                        List.of(new PetSummaryResponse(
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
                        )),
                        new AppliedFiltersResponse("dogs", "AVAILABLE", "PRICE_ASC")
                ));

        mockMvc.perform(get("/api/musngi/catalog/pets?category=dogs&availability=AVAILABLE&sort=PRICE_ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].category.slug").value("dogs"))
                .andExpect(jsonPath("$.appliedFilters.availability").value("AVAILABLE"));
    }
}

