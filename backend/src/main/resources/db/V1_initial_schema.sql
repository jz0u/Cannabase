-- Create strain_types table
CREATE TABLE strain_types (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create strains table
CREATE TABLE strains (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    type_id BIGINT REFERENCES strain_types(id),
    description TEXT,
    thc_content NUMERIC(5,2),
    cbd_content NUMERIC(5,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create index on strain name for faster searches
CREATE INDEX idx_strains_name ON strains(name);

-- Insert default strain types
INSERT INTO strain_types (name, description) VALUES
    ('sativa', 'Cannabis sativa is known for its energizing effects'),
    ('indica', 'Cannabis indica is known for its relaxing effects'),
    ('hybrid', 'A mix of Sativa and Indica');