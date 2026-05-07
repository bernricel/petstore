# Data Model: Pet Catalog Management and Gallery

## Entity: PetCategory

**Purpose**: Represents a supported category used by CRUD validation and gallery filters.

**Fields**:

- `id`: UUID, primary key
- `slug`: string, unique lowercase identifier such as `dogs` or `reptiles`
- `displayName`: string, unique human-readable label
- `sortOrder`: integer, controls filter ordering
- `active`: boolean, indicates whether the category is available for new or existing pets
- `createdAt`: timestamp
- `updatedAt`: timestamp

**Validation Rules**:

- `slug` MUST be unique and URL-safe
- `displayName` MUST be present and 2-50 characters
- `sortOrder` MUST be zero or greater

**Relationships**:

- One `PetCategory` has many `Pet` records

## Entity: Pet

**Purpose**: Represents a persisted catalog pet used by CRUD endpoints and the public gallery.

**Fields**:

- `id`: UUID, primary key
- `categoryId`: UUID, foreign key to `PetCategory`
- `slug`: string, unique public identifier
- `name`: string, public display name
- `breedOrType`: string, category-specific descriptor
- `summary`: string, short gallery description
- `description`: text, detailed pet content
- `priceAmount`: decimal(10,2), public listing price
- `currencyCode`: string, ISO-style currency code
- `availabilityStatus`: enum (`AVAILABLE`, `PENDING`, `UNAVAILABLE`)
- `primaryImageUrl`: string, nullable
- `galleryImageUrls`: JSON/text, nullable list of additional image URLs
- `published`: boolean, controls public gallery visibility
- `createdAt`: timestamp
- `updatedAt`: timestamp

**Validation Rules**:

- `slug` MUST be unique and URL-safe
- `name` MUST be present and 2-120 characters
- `breedOrType` MUST be present and 2-80 characters
- `summary` MUST be present and sized for card display
- `description` MAY be empty but detail pages must still render gracefully
- `priceAmount` MUST be zero or greater
- `availabilityStatus` MUST be one of the supported enum values
- `primaryImageUrl` MAY be null; gallery UI must show a fallback state when absent
- `published` MUST determine whether the pet appears in public gallery results

**Relationships**:

- Many `Pet` records belong to one `PetCategory`

## Value Object: CreatePetRequest

**Purpose**: Captures the required fields for creating a pet through the API.

**Fields**:

- `categorySlug`
- `slug`
- `name`
- `breedOrType`
- `summary`
- `description`
- `priceAmount`
- `currencyCode`
- `availabilityStatus`
- `primaryImageUrl`
- `galleryImageUrls`
- `published`

## Value Object: UpdatePetRequest

**Purpose**: Captures the mutable fields for updating an existing pet through the API.

**Fields**:

- All `CreatePetRequest` fields except the stable identifier

## Value Object: GalleryFilter

**Purpose**: Captures shopper-selected refinements applied to gallery retrieval.

**Fields**:

- `categorySlug`: optional selected category
- `availability`: optional availability filter
- `sortBy`: enum (`FEATURED`, `PRICE_ASC`, `PRICE_DESC`, `NAME_ASC`, `NEWEST`)

**Validation Rules**:

- `categorySlug` MUST match an active category when provided
- `availability` MUST be one of the supported statuses when provided
- `sortBy` MUST default to `FEATURED` when omitted

## Read Model: GalleryPetCard

**Purpose**: Defines the minimal data returned for gallery card display.

**Fields**:

- `id`
- `slug`
- `name`
- `category`
- `breedOrType`
- `summary`
- `priceAmount`
- `currencyCode`
- `availabilityStatus`
- `primaryImageUrl`

## Read Model: PetDetailView

**Purpose**: Defines the richer data returned for a gallery detail page.

**Fields**:

- All `GalleryPetCard` fields
- `description`
- `galleryImageUrls`
- `lastUpdatedAt`
