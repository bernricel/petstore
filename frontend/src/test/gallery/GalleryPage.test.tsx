import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { render, screen, waitFor } from "@testing-library/react";
import { MemoryRouter } from "react-router-dom";
import { afterEach, describe, expect, it, vi } from "vitest";
import { GalleryPage } from "../../features/gallery/GalleryPage";

const fetchMock = vi.fn();

function renderGalleryPage() {
  const queryClient = new QueryClient({
    defaultOptions: {
      queries: {
        retry: false,
      },
    },
  });
  return render(
    <QueryClientProvider client={queryClient}>
      <MemoryRouter>
        <GalleryPage />
      </MemoryRouter>
    </QueryClientProvider>,
  );
}

describe("GalleryPage", () => {
  afterEach(() => {
    vi.restoreAllMocks();
    fetchMock.mockReset();
  });

  it("renders gallery cards and browsing-only CTA", async () => {
    vi.stubGlobal("fetch", fetchMock);
    fetchMock
      .mockResolvedValueOnce({
        ok: true,
        json: async () => ({
          categories: [{ id: "1", slug: "dogs", displayName: "Dogs" }],
        }),
      })
      .mockResolvedValueOnce({
        ok: true,
        json: async () => ({
          items: [
            {
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
            },
          ],
          appliedFilters: { sort: "FEATURED" },
        }),
      });

    renderGalleryPage();

    expect(await screen.findByText("Buddy")).toBeInTheDocument();
    expect(screen.getByRole("link", { name: "View Details" })).toBeInTheDocument();
    expect(screen.queryByText(/add to cart/i)).not.toBeInTheDocument();
  });

  it("renders an empty-state message when no pets match", async () => {
    vi.stubGlobal("fetch", fetchMock);
    fetchMock
      .mockResolvedValueOnce({
        ok: true,
        json: async () => ({
          categories: [],
        }),
      })
      .mockResolvedValueOnce({
        ok: true,
        json: async () => ({
          items: [],
          appliedFilters: { sort: "FEATURED" },
        }),
      });

    renderGalleryPage();

    await waitFor(() => {
      expect(screen.getByText(/no pets are available right now/i)).toBeInTheDocument();
    });
  });
});
