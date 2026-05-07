import { Button, Card, CardActions, CardContent, CardMedia, Stack, Typography } from "@mui/material";
import { Link } from "react-router-dom";
import type { PetSummary } from "../services/types";
import { AvailabilityBadge } from "./AvailabilityBadge";

const fallbackImage =
  "https://images.unsplash.com/photo-1548767797-d8c844163c4c?auto=format&fit=crop&w=1200&q=80";

export function GalleryCard({ pet }: { pet: PetSummary }) {
  return (
    <Card className="gallery-card h-full">
      <CardMedia
        component="img"
        height="220"
        image={pet.primaryImageUrl || fallbackImage}
        alt={pet.name}
      />
      <CardContent>
        <Stack direction="row" justifyContent="space-between" alignItems="start" spacing={1}>
          <div>
            <Typography variant="h6" fontWeight={700}>
              {pet.name}
            </Typography>
            <Typography color="text.secondary">{pet.category.displayName} · {pet.breedOrType}</Typography>
          </div>
          <AvailabilityBadge status={pet.availabilityStatus} />
        </Stack>
        <Typography className="mt-3" color="text.secondary">
          {pet.summary}
        </Typography>
        <Typography className="mt-4" variant="h6" color="primary">
          {pet.currencyCode} {pet.priceAmount.toFixed(2)}
        </Typography>
      </CardContent>
      <CardActions className="px-4 pb-4">
        <Button component={Link} to={`/pets/${pet.id}`} variant="contained" color="primary">
          View Details
        </Button>
      </CardActions>
    </Card>
  );
}

