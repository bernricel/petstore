import Chip from "@mui/material/Chip";
import type { AvailabilityStatus } from "../services/types";

const palette: Record<AvailabilityStatus, "success" | "warning" | "default"> = {
  AVAILABLE: "success",
  PENDING: "warning",
  UNAVAILABLE: "default",
};

export function AvailabilityBadge({ status }: { status: AvailabilityStatus }) {
  return <Chip color={palette[status]} label={status.replace("_", " ")} size="small" />;
}

