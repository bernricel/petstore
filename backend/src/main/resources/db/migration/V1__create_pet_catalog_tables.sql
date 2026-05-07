CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TABLE pet_categories (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    slug VARCHAR(50) NOT NULL UNIQUE,
    display_name VARCHAR(50) NOT NULL UNIQUE,
    sort_order INTEGER NOT NULL DEFAULT 0,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE pets (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    category_id UUID NOT NULL REFERENCES pet_categories(id),
    slug VARCHAR(120) NOT NULL UNIQUE,
    name VARCHAR(120) NOT NULL,
    breed_or_type VARCHAR(80) NOT NULL,
    summary VARCHAR(255) NOT NULL,
    description TEXT,
    price_amount NUMERIC(10, 2) NOT NULL,
    currency_code VARCHAR(3) NOT NULL,
    availability_status VARCHAR(20) NOT NULL,
    primary_image_url VARCHAR(500),
    gallery_image_urls TEXT,
    published BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_pet_categories_slug ON pet_categories(slug);
CREATE INDEX idx_pets_category_id ON pets(category_id);
CREATE INDEX idx_pets_slug ON pets(slug);
CREATE INDEX idx_pets_published ON pets(published);
CREATE INDEX idx_pets_availability_status ON pets(availability_status);

