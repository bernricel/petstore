import { apiFetch } from "./api";
import type { AvailabilityStatus, CategoryListResponse, PetListResponse, SortOption } from "./types";

export function fetchCategories() {
  return apiFetch<CategoryListResponse>("/categories");
}

export function fetchGallery(params: {
  category?: string;
  availability?: AvailabilityStatus;
  sort?: SortOption;
}) {
  const search = new URLSearchParams();

  if (params.category) {
    search.set("category", params.category);
  }

  if (params.availability) {
    search.set("availability", params.availability);
  }

  if (params.sort) {
    search.set("sort", params.sort);
  }

  const suffix = search.toString() ? `?${search.toString()}` : "";
  return apiFetch<PetListResponse>(`/pets${suffix}`);
}

