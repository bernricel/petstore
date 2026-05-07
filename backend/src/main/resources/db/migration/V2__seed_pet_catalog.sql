INSERT INTO pet_categories (slug, display_name, sort_order, active)
VALUES
    ('dogs', 'Dogs', 1, TRUE),
    ('cats', 'Cats', 2, TRUE),
    ('birds', 'Birds', 3, TRUE),
    ('reptiles', 'Reptiles', 4, TRUE),
    ('fishes', 'Fishes', 5, TRUE)
ON CONFLICT (slug) DO NOTHING;

INSERT INTO pets (
    category_id,
    slug,
    name,
    breed_or_type,
    summary,
    description,
    price_amount,
    currency_code,
    availability_status,
    primary_image_url,
    gallery_image_urls,
    published
)
SELECT c.id,
       seed.slug,
       seed.name,
       seed.breed_or_type,
       seed.summary,
       seed.description,
       seed.price_amount,
       seed.currency_code,
       seed.availability_status,
       seed.primary_image_url,
       seed.gallery_image_urls,
       seed.published
FROM (
    VALUES
        ('dogs', 'golden-buddy', 'Buddy', 'Golden Retriever', 'Friendly family dog ready for play.', 'Buddy is a gentle Golden Retriever with a playful personality and strong social skills.', 1200.00, 'USD', 'AVAILABLE', 'https://images.unsplash.com/photo-1558788353-f76d92427f16', '["https://images.unsplash.com/photo-1558788353-f76d92427f16","https://images.unsplash.com/photo-1517849845537-4d257902454a"]', TRUE),
        ('cats', 'luna-whiskers', 'Luna', 'British Shorthair', 'Calm indoor cat with a soft silver coat.', 'Luna loves quiet spaces, window naps, and gentle attention throughout the day.', 850.00, 'USD', 'PENDING', 'https://images.unsplash.com/photo-1519052537078-e6302a4968d4', '["https://images.unsplash.com/photo-1519052537078-e6302a4968d4"]', TRUE),
        ('birds', 'rio-singer', 'Rio', 'Cockatiel', 'Bright and curious bird with a cheerful chirp.', 'Rio enjoys perches near natural light and responds well to regular social interaction.', 320.00, 'USD', 'AVAILABLE', 'https://images.unsplash.com/photo-1444464666168-49d633b86797', '["https://images.unsplash.com/photo-1444464666168-49d633b86797"]', TRUE),
        ('reptiles', 'sage-gecko', 'Sage', 'Leopard Gecko', 'Low-maintenance reptile with a calm temperament.', 'Sage thrives in a warm habitat with regular feeding and gentle handling routines.', 180.00, 'USD', 'AVAILABLE', 'https://images.unsplash.com/photo-1531386151447-fd76ad50012f', '["https://images.unsplash.com/photo-1531386151447-fd76ad50012f"]', TRUE),
        ('fishes', 'marlin-glow', 'Glow', 'Betta Fish', 'Vibrant freshwater fish with flowing fins.', 'Glow adds color to a planted tank and does best with steady water conditions.', 45.00, 'USD', 'UNAVAILABLE', 'https://images.unsplash.com/photo-1524704654690-b56c05c78a00', '["https://images.unsplash.com/photo-1524704654690-b56c05c78a00"]', TRUE)
) AS seed(category_slug, slug, name, breed_or_type, summary, description, price_amount, currency_code, availability_status, primary_image_url, gallery_image_urls, published)
JOIN pet_categories c ON c.slug = seed.category_slug
ON CONFLICT (slug) DO NOTHING;

