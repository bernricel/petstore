import { createBrowserRouter } from "react-router-dom";
import { GalleryPage } from "../features/gallery/GalleryPage";
import { PetDetailPage } from "../features/pet-details/PetDetailPage";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <GalleryPage />,
  },
  {
    path: "/pets/:petId",
    element: <PetDetailPage />,
  },
]);

