import { useQuery } from "@tanstack/react-query";
import {
  Alert,
  Box,
  Button,
  Chip,
  CircularProgress,
  Container,
  Divider,
  Stack,
  Typography,
} from "@mui/material";
import { Link, useParams } from "react-router-dom";
import { AvailabilityBadge } from "../../components/AvailabilityBadge";
import { fetchPetDetail } from "../../services/petApi";
import "./pet-detail.css";

const fallbackImage =
  "https://images.unsplash.com/photo-1517849845537-4d257902454a?auto=format&fit=crop&w=1200&q=80";

export function PetDetailPage() {
  const { petId = "" } = useParams();
  const petQuery = useQuery({
    queryKey: ["pet-detail", petId],
    queryFn: () => fetchPetDetail(petId),
    enabled: Boolean(petId),
  });

  return (
    <Container maxWidth="lg" className="py-10">
      <Stack spacing={3}>
        <Button component={Link} to="/" variant="text">
          Back to Gallery
        </Button>

        {petQuery.isPending ? (
          <Box className="flex min-h-64 items-center justify-center">
            <CircularProgress />
          </Box>
        ) : null}

        {petQuery.isError ? (
          <Alert severity="error">{(petQuery.error as Error).message}</Alert>
        ) : null}

        {petQuery.data ? (
          <div className="detail-shell">
            <div className="detail-image-panel">
              <img
                src={petQuery.data.primaryImageUrl || fallbackImage}
                alt={petQuery.data.name}
                className="detail-image"
              />
            </div>
            <div className="detail-info-panel">
              <Stack spacing={3}>
                <div>
                  <Typography variant="overline" color="secondary" fontWeight={700}>
                    {petQuery.data.category.displayName}
                  </Typography>
                  <Typography variant="h3" component="h1">
                    {petQuery.data.name}
                  </Typography>
                  <Typography variant="h6" color="text.secondary">
                    {petQuery.data.breedOrType}
                  </Typography>
                </div>

                <Stack direction={{ xs: "column", sm: "row" }} spacing={2} alignItems={{ sm: "center" }}>
                  <AvailabilityBadge status={petQuery.data.availabilityStatus} />
                  <Chip label={`${petQuery.data.currencyCode} ${petQuery.data.priceAmount.toFixed(2)}`} color="primary" />
                  <Chip label={`Updated ${new Date(petQuery.data.lastUpdatedAt).toLocaleDateString()}`} variant="outlined" />
                </Stack>

                <Typography variant="body1">{petQuery.data.summary}</Typography>
                <Divider />
                <div>
                  <Typography variant="h6" gutterBottom>
                    About this pet
                  </Typography>
                  <Typography variant="body1">
                    {petQuery.data.description || "Additional details will be shared soon."}
                  </Typography>
                </div>
              </Stack>
            </div>
          </div>
        ) : null}
      </Stack>
    </Container>
  );
}

