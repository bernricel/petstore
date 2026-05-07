export type AvailabilityStatus = "AVAILABLE" | "PENDING" | "UNAVAILABLE";
export type SortOption = "FEATURED" | "PRICE_ASC" | "PRICE_DESC" | "NAME_ASC" | "NEWEST";

export interface Category {
  id: string;
  slug: string;
  displayName: string;
}

export interface PetSummary {
  id: string;
  slug: string;
  name: string;
  category: Category;
  breedOrType: string;
  summary: string;
  priceAmount: number;
  currencyCode: string;
  availabilityStatus: AvailabilityStatus;
  primaryImageUrl?: string | null;
}

export interface PetDetail extends PetSummary {
  description: string;
  galleryImageUrls: string[];
  published: boolean;
  lastUpdatedAt: string;
}

export interface AppliedFilters {
  category?: string | null;
  availability?: AvailabilityStatus | null;
  sort: SortOption;
}

export interface CategoryListResponse {
  categories: Category[];
}

export interface PetListResponse {
  items: PetSummary[];
  appliedFilters: AppliedFilters;
}

