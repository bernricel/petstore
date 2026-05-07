# Research: Pet Catalog Management and Gallery

## Decision 1: Expose unauthenticated CRUD REST endpoints under `/api/musngi/catalog`

**Decision**: Use REST endpoints under `/api/musngi/catalog` for create, read, update,
delete, category listing, and filtered gallery retrieval without authentication in this
feature iteration.

**Rationale**: The required scope explicitly includes CRUD with no auth for now. Keeping
the API unauthenticated avoids introducing extra infrastructure before the core catalog
workflow is validated.

**Alternatives considered**:

- Add admin authentication now: Rejected because the current feature direction explicitly
  excludes auth for this iteration.
- Split CRUD and gallery into separate features immediately: Rejected because the chosen
  direction is a combined CRUD-first feature with gallery support.

## Decision 2: Keep categories as first-class persisted records

**Decision**: Store pet categories in a dedicated table referenced by pet records.

**Rationale**: Persisted categories support payload validation, filter population, and
consistent gallery organization across CRUD and browse flows.

**Alternatives considered**:

- Enum-only categories in code: Rejected because it makes management and seed updates less
  flexible.
- Free-form category strings: Rejected because it weakens validation and filter quality.

## Decision 3: Use PostgreSQL with Flyway for persistence and repeatability

**Decision**: Persist categories and pets in PostgreSQL and manage schema evolution with
Flyway migrations and seed-ready startup data.

**Rationale**: The feature explicitly requires persistent Postgres storage and the
constitution requires versioned schema changes.

**Alternatives considered**:

- In-memory storage for MVP: Rejected because persistence is a required feature.
- Hibernate auto-DDL only: Rejected because it does not provide explicit migration
  control across environments.

## Decision 4: Reuse the same pet data model for CRUD and gallery reads

**Decision**: Use one persisted pet model for write operations and derive dedicated DTOs
for API responses and gallery presentation.

**Rationale**: This keeps write and read flows consistent while preserving layered
boundaries between persistence and UI contracts.

**Alternatives considered**:

- Separate management and gallery data stores: Rejected because it adds needless
  synchronization complexity.
- Expose JPA entities directly: Rejected because it increases coupling and accidental
  overexposure risk.

## Decision 5: Support gallery filtering with query parameters on the list endpoint

**Decision**: Implement category, availability, and sort refinement as query parameters
on the pet listing endpoint.

**Rationale**: This keeps the API compact and lets the React gallery request only the data
it needs.

**Alternatives considered**:

- Client-side filtering after fetching all pets: Rejected because payload size and
  responsiveness degrade as the catalog grows.
- Separate endpoint per filter: Rejected because it fragments the contract unnecessarily.

## Decision 6: Validate critical CRUD and gallery behavior at both backend and frontend layers

**Decision**: Cover CRUD flows with Spring Boot contract/integration tests and cover the
gallery with React Testing Library/Vitest plus API integration checks.

**Rationale**: The constitution requires test-gated delivery for business logic and
critical user journeys.

**Alternatives considered**:

- Backend-only tests: Rejected because gallery rendering and no-purchase UI constraints
  would remain unverified.
- Manual-only QA: Rejected because it does not provide repeatable regression protection.

## Decision 7: Deploy backend and frontend separately with local Docker Compose parity

**Decision**: Use one Render web service for the Spring Boot API, one Render static site
for the React gallery, and local Docker Compose for PostgreSQL-backed development.

**Rationale**: This matches the approved stack and keeps local validation close to the
deployment shape while staying within Render free-tier expectations.

**Alternatives considered**:

- Single combined container: Rejected because it obscures deployment boundaries and
  complicates static asset hosting.
- Paid external management services: Rejected because they are outside the current scope
  and cost profile.
