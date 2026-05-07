import { useMemo, useState } from "react";
import { useQuery } from "@tanstack/react-query";
import { Alert, Box, CircularProgress, Container, Stack, Typography } from "@mui/material";
import Grid2 from "@mui/material/Grid2";
import { GalleryCard } from "../../components/GalleryCard";
import { GalleryFilters } from "../../components/GalleryFilters";
import { fetchCategories, fetchGallery } from "../../services/galleryApi";
import type { AvailabilityStatus, SortOption } from "../../services/types";
import "./gallery.css";

export function GalleryPage() {
  const [category, setCategory] = useState("");
  const [availability, setAvailability] = useState("");
  const [sort, setSort] = useState<SortOption>("FEATURED");

  const categoryQuery = useQuery({
    queryKey: ["categories"],
    queryFn: fetchCategories,
  });

  const galleryQuery = useQuery({
    queryKey: ["gallery", category, availability, sort],
    queryFn: () =>
      fetchGallery({
        category: category || undefined,
        availability: (availability || undefined) as AvailabilityStatus | undefined,
        sort,
      }),
  });

  const emptyMessage = useMemo(() => {
    if (category || availability) {
      return "No pets match the current filters.";
    }
    return "No pets are available right now.";
  }, [availability, category]);

  return (
    <div className="gallery-shell">
      <Container maxWidth="lg" className="py-10">
        <Stack spacing={4}>
          <Box className="gallery-hero">
            <Typography variant="overline" color="secondary" fontWeight={700}>
              Browse-Only Pet Gallery
            </Typography>
            <Typography variant="h3" component="h1" className="mt-3">
              Meet pets across dogs, cats, birds, reptiles, and fishes.
            </Typography>
            <Typography variant="body1" className="mt-4 max-w-2xl">
              Explore a polished catalog with clear filters, availability badges, and
              organized pet details. This experience stays focused on browsing only.
            </Typography>
          </Box>

          <GalleryFilters
            categories={categoryQuery.data?.categories ?? []}
            selectedCategory={category}
            selectedAvailability={availability}
            selectedSort={sort}
            onCategoryChange={setCategory}
            onAvailabilityChange={setAvailability}
            onSortChange={setSort}
          />

          {galleryQuery.isPending ? (
            <Box className="flex min-h-64 items-center justify-center">
              <CircularProgress />
            </Box>
          ) : null}

          {galleryQuery.isError ? (
            <Alert severity="error">{(galleryQuery.error as Error).message}</Alert>
          ) : null}

          {!galleryQuery.isPending && !galleryQuery.isError && galleryQuery.data?.items.length === 0 ? (
            <Alert severity="info">{emptyMessage}</Alert>
          ) : null}

          <Grid2 container spacing={3}>
            {galleryQuery.data?.items.map((pet) => (
              <Grid2 key={pet.id} size={{ xs: 12, sm: 6, lg: 4 }}>
                <GalleryCard pet={pet} />
              </Grid2>
            ))}
          </Grid2>
        </Stack>
      </Container>
    </div>
  );
}
