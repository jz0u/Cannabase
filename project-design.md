
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

CREATE TABLE strains (
    id bigint NOT NULL DEFAULT nextval('strains_id_seq'::regclass),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    type_id bigint,
    description text COLLATE pg_catalog."default",
    thc_content numeric(5,2),
    cbd_content numeric(5,2),
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT strains_pkey PRIMARY KEY (id),
    CONSTRAINT strains_name_key UNIQUE (name),
    CONSTRAINT strains_type_id_fkey FOREIGN KEY (type_id)
        REFERENCES public.strain_types (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);


CREATE TABLE strain_types (
    id bigint NOT NULL DEFAULT nextval('strain_types_id_seq'::regclass),
    name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    description text COLLATE pg_catalog."default",
    created_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT strain_types_pkey PRIMARY KEY (id),
    CONSTRAINT strain_types_name_key UNIQUE (name)
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