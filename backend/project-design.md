
# Cannabase

## Core Features
1. View and search strains
2. Basic strain details display
3. Filter by type
4. Simple, clean UI

## Technical Stack

### Frontend
- **Framework:** React + Vite
  - Simple setup
  - Fast development
- **Styling:** Tailwind CSS
  - Quick styling
  - No extra UI libraries needed
- **State:** React Context
  - Built-in solution
  - Sufficient for basic state needs
- **API Calls:** Fetch API
  - No extra dependencies
  - Built into browsers

### Backend
- **Framework:** Spring Boot
- **Database:** PostgreSQL
- **API:** REST endpoints

## Database Schema

```sql
-- Simple, focused schema
CREATE TABLE strains (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    type VARCHAR(50) NOT NULL,  -- Simplified to direct type string
    description TEXT,
    thc_percentage DECIMAL(5,2),
    cbd_percentage DECIMAL(5,2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Optional: Basic effects table if needed
CREATE TABLE strain_effects (
    strain_id BIGINT REFERENCES strains(id),
    effect_name VARCHAR(100) NOT NULL,
    PRIMARY KEY (strain_id, effect_name)
);
```

## API Endpoints

### Essential Routes Only
```
GET /api/strains         - List all strains (with pagination)
GET /api/strains/{id}    - Get single strain
GET /api/strains/search  - Search strains by name
```

## Project Structure

```
project/
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   │   ├── StrainList.jsx
│   │   │   ├── StrainCard.jsx
│   │   │   └── SearchBar.jsx
│   │   ├── App.jsx
│   │   └── main.jsx
│   └── package.json
│
└── backend/
    └── src/
        └── main/
            ├── java/
            │   └── com/cannabase/
            │       ├── controllers/
            │       ├── models/
            │       └── services/
            └── resources/
                └── application.properties
```

## Implementation Phases

1. **Phase 1: Basic Setup**
   - Set up React frontend with Vite
   - Initialize Spring Boot backend
   - Create database schema

2. **Phase 2: Core Features**
   - Implement strain listing and details
   - Add basic search functionality
   - Create simple UI components

3. **Phase 3: Polish**
   - Add loading states
   - Implement error handling
   - Basic responsive design