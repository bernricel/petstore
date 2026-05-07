# Quickstart: Pet Catalog Management and Gallery

## Prerequisites

- Java 21
- Node.js 20+
- Docker Desktop

## Local Development Setup

1. Start local dependencies with `docker compose -f infra/docker/docker-compose.yml up -d postgres`.
2. Launch the Spring Boot backend with `.\backend\dev.ps1` so it uses PostgreSQL
   connection settings pointed at the
   Docker database.
3. Install frontend dependencies with `npm.cmd install --prefix frontend`.
4. Launch the React frontend with `npm.cmd run dev --prefix frontend` and configure it to
   call the backend base URL.
5. Ensure the seeded categories and sample pets are loaded before CRUD or gallery checks.

## API Verification Flow

1. Create a pet through `POST /api/musngi/catalog/pets` with a valid category slug and
   gallery-visible data.
2. Read back the created pet through `GET /api/musngi/catalog/pets/{petId}` and confirm
   the returned fields match the submitted payload.
3. Update the same pet through `PUT /api/musngi/catalog/pets/{petId}` and verify the
   changes appear in a follow-up read.
4. Delete the pet through `DELETE /api/musngi/catalog/pets/{petId}` and verify it no
   longer appears in subsequent reads.
5. Confirm CRUD requests work without authentication for this feature iteration.
6. Confirm the CRUD endpoints are intentionally public and unauthenticated in both local
   and deployed environments for this iteration.
7. Example create payload:

```json
{
  "categorySlug": "dogs",
  "slug": "buddy",
  "name": "Buddy",
  "breedOrType": "Golden Retriever",
  "summary": "Friendly family dog ready for play.",
  "description": "Buddy is a gentle Golden Retriever.",
  "priceAmount": 1200.0,
  "currencyCode": "USD",
  "availabilityStatus": "AVAILABLE",
  "published": true
}
```

## Gallery Verification Flow

1. Open the public gallery page.
2. Confirm the shopper sees a responsive grid of pet cards from the seeded sample data.
3. Apply a category filter and verify only matching pets remain visible.
4. Apply an additional refinement such as availability or sorting and verify the visible
   results update correctly.
5. Open a pet detail view and confirm the shopper sees name, category, price,
   availability, imagery, and descriptive content.
6. Verify that a missing-image pet still renders with a fallback state.
7. Verify that no purchase-related CTA appears anywhere in the gallery or detail flow.

## Test Commands

1. Run backend unit and integration tests with `.\backend\test.ps1`.
2. Run frontend component and interaction tests with `npm.cmd run test --prefix frontend`.
3. Optionally validate the OpenAPI contract against the running backend, including the
   `/api/musngi/catalog` routes.

## Performance Validation Setup

- Run the backend locally with `.\backend\dev.ps1`.
- Run PostgreSQL locally through the standard Docker Compose setup in
  `infra/docker/docker-compose.yml`.
- Use the seeded sample pet catalog dataset that includes the required categories and
  sample pets.
- Measure three interactions: initial catalog list load, category filter refresh, and
  pet detail page load.
- Use browser DevTools Network timing for frontend gallery/detail flows and curl or
  Postman response times for API calls; record three runs and confirm each measured
  action is under 2 seconds.
- Expected result: each measured browsing or detail action completes within 2 seconds.
- Verification note: local timing results should be recorded here after the first full
  environment run; they are not yet captured in source control.

## UI Acceptance Examples

- Gallery example: responsive pet cards show an image area, pet name, category/breed
  text, availability badge, and a clear `View Details` browsing-only action with clean
  spacing and readable typography.
- Detail example: a large image/header area and grouped pet information sections present
  availability, description, and related details in an organized layout with no cart,
  checkout, buy, login-to-purchase, or other transaction CTA.

## Deployment Notes

- Deploy the backend to a Render web service with environment variables for PostgreSQL,
  port binding, and allowed frontend origin.
- Deploy the frontend as a Render static site with the backend base URL configured at
  build time.
- Ensure the deployed database has completed migrations before validating CRUD and gallery
  flows.
