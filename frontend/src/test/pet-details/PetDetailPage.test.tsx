import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { render, screen } from "@testing-library/react";
import { MemoryRouter, Route, Routes } from "react-router-dom";
import { afterEach, describe, expect, it, vi } from "vitest";
import { PetDetailPage } from "../../features/pet-details/PetDetailPage";

const fetchMock = vi.fn();

function renderPetDetailPage() {
  const queryClient = new QueryClient({
    defaultOptions: {
      queries: {
        retry: false,
      },
    },
  });
  return render(
    <QueryClientProvider client={queryClient}>
      <MemoryRouter initialEntries={["/pets/pet-1"]}>
        <Routes>
          <Route path="/pets/:petId" element={<PetDetailPage />} />
        </Routes>
      </MemoryRouter>
    </QueryClientProvider>,
  );
}

describe("PetDetailPage", () => {
  afterEach(() => {
    vi.restoreAllMocks();
    fetchMock.mockReset();
  });

  it("renders organized pet details without purchase controls", async () => {
    vi.stubGlobal("fetch", fetchMock);
    fetchMock.mockResolvedValueOnce({
      ok: true,
      json: async () => ({
        id: "pet-1",
        slug: "buddy",
        name: "Buddy",
        category: { id: "1", slug: "dogs", displayName: "Dogs" },
        breedOrType: "Golden Retriever",
        summary: "Friendly family dog ready for play.",
        priceAmount: 1200,
        currencyCode: "USD",
        availabilityStatus: "AVAILABLE",
        primaryImageUrl: null,
        description: "Buddy is a gentle Golden Retriever.",
        galleryImageUrls: [],
        published: true,
        lastUpdatedAt: "2026-05-07T00:00:00Z",
      }),
    });

    renderPetDetailPage();

    expect(await screen.findByRole("heading", { name: "Buddy" })).toBeInTheDocument();
    expect(screen.getByText(/about this pet/i)).toBeInTheDocument();
    expect(screen.queryByText(/buy now/i)).not.toBeInTheDocument();
  });
});
