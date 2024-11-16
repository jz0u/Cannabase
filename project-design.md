# Cannabase - Cannabis Strain Information System

## Project Overview
Cannabase is a full-stack application for managing and searching cannabis strain information. The system provides a RESTful API backend built with Spring Boot and a React frontend for accessing strain data.

## Core Features
1. View and search strains with filtering capabilities
2. Detailed strain information display
3. Filter strains by type (Sativa, Indica, Hybrid)
4. Manage strain types through admin interface
5. Data import functionality via CSV
6. Clean, responsive UI

## Technical Stack

### Frontend
- **Framework:** React + Vite
  - Modern build tooling
  - Fast development experience
  - Hot Module Replacement (HMR)
- **Styling:** Tailwind CSS
  - Utility-first CSS framework
  - Built-in responsive design
  - Easy theme customization
- **State Management:** React Context + Hooks
  - Built-in React solutions
  - Simpler than Redux for our needs
- **API Integration:** Axios
  - Promise-based HTTP client
  - Consistent error handling
  - Request/response interceptors

### Backend
- **Framework:** Spring Boot 3
  - Production-ready features
  - Built-in security
  - Extensive documentation
- **Database:** PostgreSQL
  - Robust relational database
  - JSONB support for flexible data
  - Strong data integrity
- **API:** REST endpoints with versioning
  - Clear versioning strategy (v1)
  - Standardized response formats
  - Comprehensive error handling

## Database Schema

### Core Tables

#### strains
```sql
CREATE TABLE strains (
    id bigint PRIMARY KEY,
    name varchar(255) UNIQUE NOT NULL,
    type_id bigint REFERENCES strain_types(id),
    description text,
    thc_content numeric(5,2),
    cbd_content numeric(5,2),
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP
);
```

#### strain_types
```sql
CREATE TABLE strain_types (
    id bigint PRIMARY KEY,
    name varchar(50) UNIQUE NOT NULL,
    description text,
    created_at timestamp DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp DEFAULT CURRENT_TIMESTAMP
);
```

## API Endpoints

### Strains API (v1)
```
GET     /api/v1/strains          - List all strains (paginated)
GET     /api/v1/strains/{id}     - Get strain details
GET     /api/v1/strains/search   - Search strains by name
POST    /api/v1/strains          - Create new strain
PUT     /api/v1/strains/{id}     - Update strain
DELETE  /api/v1/strains/{id}     - Delete strain
```

### Strain Types API (v1)
```
GET     /api/v1/strain-types          - List all strain types
GET     /api/v1/strain-types/{id}     - Get strain type details
POST    /api/v1/strain-types          - Create strain type
PUT     /api/v1/strain-types/{id}     - Update strain type
DELETE  /api/v1/strain-types/{id}     - Delete strain type
```

## Project Structure

```
project/
├── frontend/
│   ├── src/
│   │   ├── components/
│   │   │   ├── strains/
│   │   │   │   ├── StrainList.jsx
│   │   │   │   ├── StrainCard.jsx
│   │   │   │   └── StrainDetail.jsx
│   │   │   ├── common/
│   │   │   │   ├── SearchBar.jsx
│   │   │   │   └── LoadingSpinner.jsx
│   │   │   └── layout/
│   │   ├── hooks/
│   │   ├── services/
│   │   ├── utils/
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
            │       ├── services/
            │       ├── repositories/
            │       ├── models/
            │       ├── dto/
            │       ├── mapper/
            │       └── exceptions/
            └── resources/
                └── application.properties
```

## Development Workflow

1. **Git Workflow**
   - Main branch: `main` (production)
   - Development branch: `develop`
   - Feature branches: `feature/feature-name`
   - Hotfix branches: `hotfix/issue-description`

2. **Development Process**
   ```bash
   # Create feature branch
   git checkout develop
   git pull origin develop
   git checkout -b feature/your-feature
   
   # Make changes and commit
   git add .
   git commit -m "feat: your feature description"
   
   # Push changes
   git push origin feature/your-feature
   ```

3. **Running the Application**
   ```bash
   # Backend
   cd backend
   ./mvnw spring-boot:run
   
   # Frontend
   cd frontend
   npm install
   npm run dev
   ```

## Next Steps

1. **Phase 1: Core Implementation**
   - [x] Basic backend CRUD operations
   - [x] Data import functionality
   - [ ] Frontend components development
   - [ ] Integration testing

2. **Phase 2: Enhanced Features**
   - [ ] User authentication
   - [ ] Advanced search capabilities
   - [ ] Strain ratings and reviews
   - [ ] Image upload support

3. **Phase 3: Production Readiness**
   - [ ] Performance optimization
   - [ ] Security hardening
   - [ ] Monitoring setup
   - [ ] Deployment automation