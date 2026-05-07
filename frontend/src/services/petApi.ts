import { apiFetch } from "./api";
import type { PetDetail } from "./types";

export function fetchPetDetail(petId: string) {
  return apiFetch<PetDetail>(`/pets/${petId}`);
}

