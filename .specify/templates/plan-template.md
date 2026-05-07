# Implementation Plan: [FEATURE]

**Branch**: `[###-feature-name]` | **Date**: [DATE] | **Spec**: [link]
**Input**: Feature specification from `/specs/[###-feature-name]/spec.md`

**Note**: This template is filled in by the `/speckit-plan` command. See `.specify/templates/plan-template.md` for the execution workflow.

## Summary

[Extract from feature spec: primary requirement + technical approach from research]

## Technical Context

<!--
  ACTION REQUIRED: Replace the content in this section with the technical details
  for the project. The structure here is presented in advisory capacity to guide
  the iteration process.
-->

**Language/Version**: [e.g., Java 21, TypeScript 5.x or NEEDS CLARIFICATION]  
**Primary Dependencies**: [e.g., Spring Boot, React, Tailwind, MUI or NEEDS CLARIFICATION]  
**Storage**: [if applicable, e.g., PostgreSQL or N/A]  
**Testing**: [e.g., JUnit, Spring Boot integration tests, Vitest, React Testing Library or NEEDS CLARIFICATION]  
**Target Platform**: [e.g., Render web service + browser clients or NEEDS CLARIFICATION]  
**Project Type**: [e.g., web application with separate frontend/backend or NEEDS CLARIFICATION]  
**Performance Goals**: [domain-specific, e.g., catalog load under 2s p95 or NEEDS CLARIFICATION]  
**Constraints**: [domain-specific, e.g., Render free-tier limits, mobile responsiveness, accessibility baseline or NEEDS CLARIFICATION]  
**Scale/Scope**: [domain-specific, e.g., MVP storefront + admin catalog management or NEEDS CLARIFICATION]  
**API/Contract Impact**: [new endpoints, DTOs, validation rules, or N/A]  
**Database/Migration Impact**: [schema changes, migration plan, rollback notes, or N/A]  
**Deployment Fit**: [Render free-tier assumptions, Docker parity notes, or NEEDS CLARIFICATION]

## Constitution Check

*GATE: Must pass before Phase 0 research. Re-check after Phase 1 design.*

- Confirm the solution preserves explicit `backend/`, `frontend/`, and infrastructure
  boundaries with no direct frontend dependence on database internals.
- Identify every API contract, DTO change, and PostgreSQL migration required by the
  feature, including any breaking-change handling.
- Define the automated tests required for the story, including backend integration tests
  and frontend component or flow coverage for critical journeys.
- Review security, privacy, accessibility, and responsive-design impact for customer and
  admin experiences.
- Verify the design fits Render free-tier deployment and local Docker parity without
  introducing unnecessary paid services or always-on worker processes.

## Project Structure

### Documentation (this feature)

```text
specs/[###-feature]/
├── plan.md              # This file (/speckit-plan command output)
├── research.md          # Phase 0 output (/speckit-plan command)
├── data-model.md        # Phase 1 output (/speckit-plan command)
├── quickstart.md        # Phase 1 output (/speckit-plan command)
├── contracts/           # Phase 1 output (/speckit-plan command)
└── tasks.md             # Phase 2 output (/speckit-tasks command - NOT created by /speckit-plan)
```

### Source Code (repository root)
<!--
  ACTION REQUIRED: Replace the placeholder tree below with the concrete layout
  for this feature. Delete unused options and expand the chosen structure with
  real paths (e.g., apps/admin, packages/something). The delivered plan must
  not include Option labels.
-->

```text
# [REMOVE IF UNUSED] Option 1: Single project
src/
├── models/
├── services/
├── cli/
└── lib/

tests/
├── contract/
├── integration/
└── unit/

# Option 2: Web application (default when frontend + backend are present)
backend/
├── src/
│   ├── models/
│   ├── services/
│   └── api/
└── tests/

frontend/
├── src/
│   ├── components/
│   ├── pages/
│   └── services/
└── tests/

infra/
├── docker/
└── render/

# [REMOVE IF UNUSED] Option 3: Mobile + API
api/
└── [same as backend above]

ios/ or android/
└── [platform-specific structure: feature modules, UI flows, platform tests]
```

**Structure Decision**: [Document the selected structure and reference the real
directories captured above]

## Complexity Tracking

> **Fill ONLY if Constitution Check has violations that must be justified**

| Violation | Why Needed | Simpler Alternative Rejected Because |
|-----------|------------|-------------------------------------|
| [e.g., 4th project] | [current need] | [why 3 projects insufficient] |
| [e.g., Repository pattern] | [specific problem] | [why direct DB access insufficient] |
