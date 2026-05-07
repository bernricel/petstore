---

description: "Task list for implementing the Pet Catalog Management and Gallery feature"
---

# Tasks: Pet Catalog Management and Gallery

**Input**: Design documents from `/specs/001-pet-browsing/`
**Prerequisites**: plan.md (required), spec.md (required for user stories), research.md, data-model.md, contracts/

**Tests**: Test tasks are REQUIRED by the constitution for backend CRUD behavior and
critical frontend gallery flows.

**Organization**: Tasks are grouped by user story so each story can be implemented,
tested, and demonstrated independently.

## Format: `[ID] [P?] [Story] Description`

- **[P]**: Can run in parallel (different files, no dependencies)
- **[Story]**: Which user story this task belongs to (e.g., US1, US2, US3)
- Include exact file paths in descriptions

## Path Conventions

- **Web app**: `backend/src/`, `frontend/src/`, `infra/`
- **Docs**: `specs/001-pet-browsing/`

## Phase 1: Setup (Shared Infrastructure)

**Purpose**: Create the project skeleton, toolchain, and implementation anchors before feature work starts.

- [X] T001 Create the backend, frontend, and infra directory structure described in `specs/001-pet-browsing/plan.md`
- [X] T002 Initialize the Spring Boot backend project in `backend/` with build files and starter dependencies
- [X] T003 [P] Initialize the React + TypeScript frontend project in `frontend/` with Vite, Tailwind, and MUI configuration
- [X] T004 [P] Create Docker scaffolding in `infra/docker/backend.Dockerfile`, `infra/docker/frontend.Dockerfile`, and `infra/docker/docker-compose.yml`
- [X] T005 Verify the canonical API prefix `/api/musngi/catalog` and Java package `com.musngi.petbrowsing` are reflected in generated scaffolding and implementation notes before T006 begins

---

## Phase 2: Foundational (Blocking Prerequisites)

**Purpose**: Build the shared platform pieces that all user stories depend on.

**CRITICAL**: No user story work can begin until this phase is complete, and T006-T041 depend on T005 being complete.

- [X] T006 Configure backend application settings, environment variable binding, and PostgreSQL connectivity in `backend/src/main/resources/application.yml`
- [X] T007 [P] Add the initial Flyway migration for pet categories and pets in `backend/src/main/resources/db/migration/V1__create_pet_catalog_tables.sql`
- [X] T008 [P] Create shared backend domain models and repositories for categories and pets in `backend/src/main/java/com/musngi/petbrowsing/categories/domain/` and `backend/src/main/java/com/musngi/petbrowsing/pets/domain/`
- [X] T009 [P] Implement shared backend error handling and API response conventions in `backend/src/main/java/com/musngi/petbrowsing/shared/`
- [X] T010 [P] Create frontend API client configuration and shared gallery data types in `frontend/src/services/api.ts` and `frontend/src/services/types.ts`
- [X] T011 Seed required categories and sample pets for local validation in `backend/src/main/resources/db/migration/V2__seed_pet_catalog.sql`
- [X] T012 Configure Render deployment assets in `infra/render/render.yaml`

**Checkpoint**: Foundation ready - user story implementation can now begin.

---

## Phase 3: User Story 1 - Create and Update Pets via API (Priority: P1) MVP

**Goal**: Deliver unauthenticated REST endpoints that create, read, and update pets in PostgreSQL.

**Independent Test**: Submit create and update requests, then confirm follow-up reads return the persisted data and validation failures are rejected clearly.

### Tests for User Story 1

> **NOTE: Write these tests FIRST, ensure they FAIL before implementation**

- [X] T013 [P] [US1] Add backend contract tests for POST, GET, and PUT pet endpoints in `backend/src/test/java/com/musngi/petbrowsing/contract/PetCrudContractTest.java`
- [X] T014 [P] [US1] Add backend integration tests for create, read, update, validation errors, and unauthenticated write access in `backend/src/test/java/com/musngi/petbrowsing/integration/PetCrudIntegrationTest.java`

### Implementation for User Story 1

- [X] T015 [P] [US1] Implement create and update request DTOs plus response DTO mapping in `backend/src/main/java/com/musngi/petbrowsing/pets/api/`
- [X] T016 [P] [US1] Implement create, read, and update services in `backend/src/main/java/com/musngi/petbrowsing/pets/application/`
- [X] T017 [US1] Implement POST, GET, and PUT pet endpoints under `/api/musngi/catalog` in `backend/src/main/java/com/musngi/petbrowsing/pets/api/PetController.java`
- [X] T018 [US1] Add category lookup, payload validation, and duplicate slug enforcement for create and update flows in `backend/src/main/java/com/musngi/petbrowsing/categories/application/` and `backend/src/main/java/com/musngi/petbrowsing/pets/application/`, rejecting duplicate slug attempts with an appropriate validation or conflict response

**Checkpoint**: User Story 1 should be fully functional and testable on its own.

---

## Phase 4: User Story 2 - Read and Delete Pets via API (Priority: P2)

**Goal**: Let API consumers list, inspect, and delete pets with clear follow-up read behavior.

**Independent Test**: List pets, retrieve a single pet, delete a pet, and confirm subsequent reads reflect the deletion result.

### Tests for User Story 2

- [X] T019 [P] [US2] Add backend contract tests for list, detail, and delete pet endpoints in `backend/src/test/java/com/musngi/petbrowsing/contract/PetDeleteAndReadContractTest.java`
- [X] T020 [P] [US2] Add backend integration tests for list retrieval, not-found handling, and delete behavior in `backend/src/test/java/com/musngi/petbrowsing/integration/PetDeleteAndReadIntegrationTest.java`

### Implementation for User Story 2

- [X] T021 [P] [US2] Implement pet listing and delete services in `backend/src/main/java/com/musngi/petbrowsing/pets/application/`
- [X] T022 [US2] Implement GET list, GET detail, and DELETE endpoints in `backend/src/main/java/com/musngi/petbrowsing/pets/api/PetController.java`
- [X] T023 [US2] Ensure delete results are reflected consistently in follow-up reads and seeded gallery data handling in `backend/src/main/java/com/musngi/petbrowsing/pets/application/`

**Checkpoint**: User Stories 1 and 2 should both work independently.

---

## Phase 5: User Story 3 - Browse the Pet Gallery (Priority: P3)

**Goal**: Deliver a responsive React product gallery grid with filters and clean pet detail views.

**Independent Test**: Open the gallery, apply filters, open a pet detail view, and confirm the layout remains clear on desktop and mobile with no purchase controls.

### Tests for User Story 3

- [X] T024 [P] [US3] Add backend contract tests for category listing and filtered gallery retrieval in `backend/src/test/java/com/musngi/petbrowsing/contract/GalleryContractTest.java`
- [X] T025 [P] [US3] Add backend integration tests for category filters, sort/refinement behavior, and gallery read performance in `backend/src/test/java/com/musngi/petbrowsing/integration/GalleryIntegrationTest.java`
- [X] T026 [P] [US3] Add frontend interaction tests for gallery loading, filters, detail navigation, and absence of purchase controls in `frontend/src/test/gallery/GalleryPage.test.tsx` and `frontend/src/test/pet-details/PetDetailPage.test.tsx`

### Implementation for User Story 3

- [X] T027 [P] [US3] Implement category listing and filtered gallery query services in `backend/src/main/java/com/musngi/petbrowsing/categories/application/` and `backend/src/main/java/com/musngi/petbrowsing/pets/application/`
- [X] T028 [US3] Implement category and gallery retrieval endpoints under `/api/musngi/catalog` in `backend/src/main/java/com/musngi/petbrowsing/categories/api/CategoryController.java` and `backend/src/main/java/com/musngi/petbrowsing/pets/api/PetController.java`
- [X] T029 [US3] Implement frontend gallery and pet detail API calls in `frontend/src/services/galleryApi.ts` and `frontend/src/services/petApi.ts`
- [X] T030 [P] [US3] Build reusable gallery card, filter, and availability badge components in `frontend/src/components/`
- [X] T031 [US3] Build the gallery page with a modern responsive card grid, polished spacing, and filter controls in `frontend/src/features/gallery/GalleryPage.tsx`
- [X] T032 [US3] Build the pet detail page with a modern organized layout and browsing-only presentation in `frontend/src/features/pet-details/PetDetailPage.tsx`
- [X] T033 [US3] Wire the gallery and detail routes in `frontend/src/app/router.tsx` and `frontend/src/app/App.tsx`
- [X] T034 [US3] Add responsive and accessible gallery/detail styling in `frontend/src/features/gallery/gallery.css`, `frontend/src/features/pet-details/pet-detail.css`, and `frontend/src/app/index.css`

**Checkpoint**: All user stories should now be independently functional.

---

## Phase 6: Polish & Cross-Cutting Concerns

**Purpose**: Finish quality, validation, and documentation work that spans multiple stories.

- [ ] T035 [P] Validate that catalog list loading, category filter updates, and pet detail loading each complete within 2 seconds while the backend runs locally with the project dev command, PostgreSQL runs through the normal Docker setup, and the seeded sample pet catalog dataset is loaded; document the measurement method and results in `specs/001-pet-browsing/quickstart.md`
- [X] T036 Add backend and frontend test runner commands to project scripts in `backend/` and `frontend/package.json`
- [X] T037 [P] Validate that the gallery and detail UI show no Add to Cart, Checkout, Buy Now, Login to Purchase, account purchase controls, or other purchase-related CTAs in `frontend/src/test/gallery/GalleryPage.test.tsx`, `frontend/src/test/pet-details/PetDetailPage.test.tsx`, and manual verification notes in `specs/001-pet-browsing/quickstart.md`
- [ ] T038 [P] Validate modern responsive UI acceptance criteria for card-based gallery layout, organized detail layout, desktop/mobile behavior, and no purchase CTAs in `frontend/src/features/gallery/`, `frontend/src/features/pet-details/`, and `specs/001-pet-browsing/quickstart.md`
- [X] T039 [P] Document unauthenticated CRUD behavior and API smoke-test examples in `specs/001-pet-browsing/quickstart.md` and `README.md`
- [ ] T040 [P] Validate that create, update, delete, and gallery read flows work against the seeded Postgres dataset in `specs/001-pet-browsing/quickstart.md`
- [ ] T041 Run the full quickstart validation flow and record any required fixes in `specs/001-pet-browsing/quickstart.md`

---

## Dependencies & Execution Order

### Phase Dependencies

- **Setup (Phase 1)**: No dependencies - can start immediately
- **Foundational (Phase 2)**: Depends on Setup completion and T005 - blocks all user stories
- **User Story 1 (Phase 3)**: Depends on Foundational completion
- **User Story 2 (Phase 4)**: Depends on User Story 1 because delete behavior relies on stable create/read flows
- **User Story 3 (Phase 5)**: Depends on Foundational completion and can begin after read endpoints are stable
- **Polish (Phase 6)**: Depends on all desired user stories being complete

### User Story Dependencies

- **User Story 1 (P1)**: Establishes core CRUD create/read/update behavior
- **User Story 2 (P2)**: Extends CRUD with delete and follow-up read consistency
- **User Story 3 (P3)**: Reuses persisted pet data to power the public gallery

### Within Each User Story

- Tests MUST be written and fail before implementation
- Backend services before endpoints
- API integration before page wiring
- Components before full page composition
- Story complete before moving to the next dependent story

### Parallel Opportunities

- `T003` and `T004` can run in parallel after backend initialization starts
- `T007`, `T008`, `T009`, and `T010` can run in parallel during Foundational work after T005 is complete
- US1 tests `T013` and `T014` can run in parallel
- US2 tests `T019` and `T020` can run in parallel
- US3 tests `T024`, `T025`, and `T026` can run in parallel
- US3 UI tasks `T030`, `T031`, and `T032` can run in parallel once API contracts are stable

---

## Parallel Example: User Story 3

```bash
# Launch US3 tests together:
Task: "Add backend contract tests in backend/src/test/java/com/musngi/petbrowsing/contract/GalleryContractTest.java"
Task: "Add backend integration tests in backend/src/test/java/com/musngi/petbrowsing/integration/GalleryIntegrationTest.java"
Task: "Add frontend gallery tests in frontend/src/test/gallery/GalleryPage.test.tsx"

# Launch US3 implementation tasks with separate files:
Task: "Implement category and gallery query services in backend/src/main/java/com/musngi/petbrowsing/"
Task: "Build reusable gallery components in frontend/src/components/"
Task: "Build the gallery and detail pages in frontend/src/features/"
```

---

## Implementation Strategy

### MVP First (User Story 1 Only)

1. Complete Phase 1: Setup
2. Complete T005 and then complete Phase 2: Foundational
3. Complete Phase 3: User Story 1
4. Stop and validate create/read/update API behavior independently
5. Demo CRUD persistence with Postgres

### Incremental Delivery

1. Ship the shared platform and seeded catalog foundation
2. Deliver US1 for create/read/update pet management
3. Deliver US2 for delete and follow-up read consistency
4. Deliver US3 for the public gallery grid and filters
5. Finish with performance, no-purchase, and quickstart validation

### Parallel Team Strategy

1. One developer handles backend foundation while another initializes the frontend shell
2. After Foundational is complete:
   - Developer A: CRUD services and controllers
   - Developer B: gallery UI and client integration
   - Developer C: deployment, seed data, and validation support
3. Converge for polish and full quickstart validation

---

## Notes

- All tasks follow the required checkbox, ID, label, and file-path format
- `T005` is an explicit blocker that must be complete before any implementation task from `T006` onward
- The final canonical values are `/api/musngi/catalog` for the API base path and `com.musngi.petbrowsing` for the Java package root
