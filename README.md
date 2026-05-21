# Petstore

Full-stack pet catalog application with a Spring Boot API, PostgreSQL persistence, a
React gallery UI, Docker-based local development, and Render deployment assets.

## Project Structure

- `backend/`: Spring Boot CRUD and gallery API under `/api/musngi/catalog`
- `frontend/`: React + TypeScript gallery UI
- `infra/`: Docker and Render deployment assets

## Local Development

See [specs/001-pet-browsing/quickstart.md](specs/001-pet-browsing/quickstart.md) for the
full setup and validation flow.

## Render Deployment

This repo includes a Render Blueprint at [infra/render/render.yaml](infra/render/render.yaml).

1. Push this repository to GitHub.
2. In Render, create a new Blueprint and point it at `infra/render/render.yaml`.
3. Render will create:
   - `petstore-db` as a managed PostgreSQL database
   - `petstore-api` as a Docker-based Spring Boot web service
   - `petstore-frontend` as a static site
4. Wait for the database to provision, then deploy the backend and frontend.
5. Open the static site URL and confirm the gallery loads and detail pages work.

Deployment wiring is automatic through the Blueprint:

- The backend reads its PostgreSQL connection string from the managed Render database.
- The frontend reads the backend public URL from the `petstore-api` service.
- The backend allows CORS requests from the `petstore-frontend` public URL.
- The static site rewrites all routes to `index.html` so React Router paths work.

## Common Commands

- Start PostgreSQL: `docker compose -f infra/docker/docker-compose.yml up -d postgres`
- Run backend: `.\backend\dev.ps1`
- Run backend tests: `.\backend\test.ps1`
- Install frontend dependencies: `npm.cmd install --prefix frontend`
- Run frontend: `npm.cmd run dev --prefix frontend`
- Run frontend tests: `npm.cmd run test --prefix frontend`

## API Smoke Test

For this iteration, all catalog CRUD endpoints are intentionally public and
unauthenticated in deployed and local environments; authentication and access controls
are out of scope.

Example create request:

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
