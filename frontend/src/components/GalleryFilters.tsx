import { FormControl, InputLabel, MenuItem, Select, Stack } from "@mui/material";
import type { AvailabilityStatus, Category, SortOption } from "../services/types";

interface GalleryFiltersProps {
  categories: Category[];
  selectedCategory: string;
  selectedAvailability: string;
  selectedSort: SortOption;
  onCategoryChange: (value: string) => void;
  onAvailabilityChange: (value: string) => void;
  onSortChange: (value: SortOption) => void;
}

export function GalleryFilters({
  categories,
  selectedCategory,
  selectedAvailability,
  selectedSort,
  onCategoryChange,
  onAvailabilityChange,
  onSortChange,
}: GalleryFiltersProps) {
  return (
    <Stack direction={{ xs: "column", md: "row" }} spacing={2}>
      <FormControl fullWidth>
        <InputLabel id="category-label">Category</InputLabel>
        <Select
          labelId="category-label"
          value={selectedCategory}
          label="Category"
          onChange={(event) => onCategoryChange(event.target.value)}
        >
          <MenuItem value="">All Categories</MenuItem>
          {categories.map((category) => (
            <MenuItem key={category.id} value={category.slug}>
              {category.displayName}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      <FormControl fullWidth>
        <InputLabel id="availability-label">Availability</InputLabel>
        <Select
          labelId="availability-label"
          value={selectedAvailability}
          label="Availability"
          onChange={(event) => onAvailabilityChange(event.target.value)}
        >
          <MenuItem value="">All Statuses</MenuItem>
          {["AVAILABLE", "PENDING", "UNAVAILABLE"].map((status) => (
            <MenuItem key={status} value={status}>
              {status.replace("_", " ")}
            </MenuItem>
          ))}
        </Select>
      </FormControl>

      <FormControl fullWidth>
        <InputLabel id="sort-label">Sort By</InputLabel>
        <Select
          labelId="sort-label"
          value={selectedSort}
          label="Sort By"
          onChange={(event) => onSortChange(event.target.value as SortOption)}
        >
          <MenuItem value="FEATURED">Featured</MenuItem>
          <MenuItem value="PRICE_ASC">Price: Low to High</MenuItem>
          <MenuItem value="PRICE_DESC">Price: High to Low</MenuItem>
          <MenuItem value="NAME_ASC">Name</MenuItem>
          <MenuItem value="NEWEST">Newest</MenuItem>
        </Select>
      </FormControl>
    </Stack>
  );
}

