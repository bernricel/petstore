<!--
Sync Impact Report
- Version change: template -> 1.0.0
- Modified principles:
  - Template Principle 1 -> I. Layered Full-Stack Boundaries
  - Template Principle 2 -> II. Contract-First Data and API Evolution
  - Template Principle 3 -> III. Test-Gated Delivery
  - Template Principle 4 -> IV. Secure and Accessible Commerce Defaults
  - Template Principle 5 -> V. Free-Tier Operability and Docker Parity
- Added sections:
  - Technology Constraints
  - Delivery Workflow and Quality Gates
- Removed sections:
  - None
- Templates requiring updates:
  - updated: .specify/templates/plan-template.md
  - updated: .specify/templates/spec-template.md
  - updated: .specify/templates/tasks-template.md
  - pending: .specify/templates/commands/*.md (directory not present in this repository)
- Follow-up TODOs:
  - None
-->
# petstore Constitution

## Core Principles

### I. Layered Full-Stack Boundaries
The application MUST remain split into explicit backend, frontend, and infrastructure
concerns. Spring Boot owns business rules, persistence, and API contracts; React owns
presentation and client interaction; Docker and deployment assets own environment parity
and runtime packaging. Shared data shapes MUST be represented through documented API
contracts and DTOs rather than direct database coupling or frontend knowledge of backend
internals. This keeps change impact local and makes the system maintainable as catalog,
inventory, and order features grow.

### II. Contract-First Data and API Evolution
Every user-visible capability MUST begin with a documented contract covering request and
response shapes, validation rules, failure states, and affected entities. PostgreSQL
schema changes MUST ship with versioned migrations, backward-compatibility expectations,
and rollback considerations. Breaking API or schema changes MUST be called out in the
plan before implementation starts. This principle protects the storefront and admin
surfaces from accidental regressions while enabling safe iteration.

### III. Test-Gated Delivery
Implementation work MUST be accompanied by automated tests at the appropriate layer:
Spring Boot unit and integration tests for business logic and persistence, and frontend
component or flow tests for critical user journeys. P1 purchase-path capabilities such as
catalog browsing, product detail viewing, cart updates, checkout preparation, and admin
catalog maintenance MUST have integration coverage before release. A task list is not
complete unless it includes the test work needed to prove the story independently.

### IV. Secure and Accessible Commerce Defaults
The system MUST treat security, privacy, and accessibility as baseline product behavior.
Inputs MUST be validated on both client and server, secrets MUST stay outside source
control, privileged actions MUST be authenticated and authorized, and personally
identifiable customer data MUST be minimized to what the feature needs. Customer-facing
flows MUST support responsive layouts and accessible semantics so browsing and checkout
remain usable on desktop and mobile. These defaults are non-negotiable for an online
store handling trust-sensitive interactions.

### V. Free-Tier Operability and Docker Parity
Architecture decisions MUST fit Render free-tier deployment and local Docker-based
development. Services MUST start with sensible defaults, document required environment
variables, and avoid mandatory paid infrastructure or always-on background processes for
the MVP. Local development and CI SHOULD use Dockerized dependencies or equivalent
configuration that mirrors production behavior closely enough to surface startup,
database, and networking issues early. Simplicity wins unless a more complex design is
needed to satisfy a clearly stated requirement.

## Technology Constraints

The approved stack for this project is Java Spring Boot for the backend API, PostgreSQL
for persistent storage, React for the frontend, Tailwind CSS and MUI for the interface
layer, and Docker for local environment consistency and deploy packaging. Deployment MUST
target Render free-tier services for the initial release. New dependencies SHOULD only be
introduced when the existing stack cannot meet the requirement with reasonable effort,
and the plan MUST justify the addition, operational cost, and maintenance burden.

Repository structure and delivery artifacts MUST reflect a web-application layout with at
least `backend/`, `frontend/`, and deployment-supporting files such as Docker
configuration, environment examples, and platform setup documentation.

## Delivery Workflow and Quality Gates

Each feature MUST move through specification, planning, and task generation before major
implementation begins. Plans MUST include a constitution check covering architecture
boundaries, API or schema contracts, required tests, security or accessibility impact,
and Render free-tier operational fit. Tasks MUST be organized by user story and include
explicit work for migrations, contracts, tests, and deployment updates when those areas
change. Reviews MUST block merges when constitution requirements are unmet or when a
simpler compliant approach has not been considered.

## Governance

This constitution overrides conflicting local habits or undocumented preferences. Changes
require: 1. an updated rationale in this file, 2. synchronization of affected templates
and workflow guidance, and 3. semantic versioning of the constitution itself. Versioning
rules are: MAJOR for incompatible governance changes or principle removal, MINOR for new
principles or materially expanded obligations, and PATCH for clarifications that do not
change enforcement. Compliance MUST be checked during planning, task generation, code
review, and release readiness review for every feature.

**Version**: 1.0.0 | **Ratified**: 2026-05-07 | **Last Amended**: 2026-05-07
