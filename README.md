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
