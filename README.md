# NearMe

A full-stack web application that helps users discover local points of interest — restaurants, movies, shops, tourist spots, events, and activities — across major Indian cities.

---

## Tech Stack

| Layer     | Technology                                      |
|-----------|-------------------------------------------------|
| Backend   | Java 17, Spring Boot 3.2, Spring Data JPA, Spring Security |
| Database  | MySQL 8 (H2 for local dev/tests)               |
| Auth      | JWT (JJWT), BCrypt password hashing            |
| Frontend  | React 18, vanilla CSS                          |
| Build     | Maven (backend), npm / Create React App (frontend) |
| Container | Docker + Docker Compose                        |
| Docs      | Springdoc OpenAPI (Swagger UI)                 |

---

## Prerequisites

- Java 17+
- Maven 3.9+
- Node.js 18+ and npm
- MySQL 8 (or use the Docker Compose setup)
- Docker & Docker Compose (optional, for containerised run)

---

## Project Structure

```
NearMe/
├── backend/          # Spring Boot application
│   ├── src/main/java/com/nearme/
│   │   ├── controller/
│   │   ├── service/impl/
│   │   ├── repository/
│   │   ├── model/
│   │   ├── dto/
│   │   ├── exception/
│   │   ├── security/
│   │   ├── config/
│   │   └── util/
│   └── src/main/resources/
│       ├── application.properties
│       └── data.sql
├── frontend/         # React application
│   └── src/
├── docker-compose.yml
└── .env.example
```

---

## Quick Start (Docker)

```bash
# 1. Copy and fill in environment variables
cp .env.example .env

# 2. Start everything
docker-compose up --build

# Backend:  http://localhost:8080
# Frontend: http://localhost:3000
# Swagger:  http://localhost:8080/swagger-ui.html
```

---

## Manual Setup

### Backend

```bash
cd backend

# Option A — use H2 (zero config, data resets on restart)
mvn spring-boot:run

# Option B — use MySQL (requires a running MySQL instance)
export DB_URL=jdbc:mysql://localhost:3306/nearme
export DB_USER=root
export DB_PASS=yourpassword
export JWT_SECRET=change-this-in-production
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`.  
Swagger UI: `http://localhost:8080/swagger-ui.html`

### Frontend

```bash
cd frontend
cp .env.example .env          # set REACT_APP_BACKEND_URL if needed
npm install
npm start                     # http://localhost:3000
```

---

## Environment Variables

| Variable       | Description                              | Default (dev)                          |
|----------------|------------------------------------------|----------------------------------------|
| `DB_URL`       | JDBC connection string                   | `jdbc:mysql://localhost:3306/nearme`   |
| `DB_USER`      | Database username                        | `root`                                 |
| `DB_PASS`      | Database password                        | *(none)*                               |
| `JWT_SECRET`   | Secret key for signing JWT tokens        | `change-this-in-production`            |
| `JWT_EXPIRY`   | Token lifetime in milliseconds           | `86400000` (24 h)                      |
| `CORS_ORIGINS` | Comma-separated list of allowed origins  | `http://localhost:3000`                |

See [`.env.example`](.env.example) for the full list.

---

## API Endpoints

All endpoints return JSON. Protected endpoints require `Authorization: Bearer <token>`.

### Authentication — `/api/auth`

| Method | Path               | Body                          | Auth | Description              |
|--------|--------------------|-------------------------------|------|--------------------------|
| POST   | `/api/auth/register` | `{name, email, password}`   | No   | Register, returns JWT    |
| POST   | `/api/auth/login`    | `{email, password}`         | No   | Login, returns JWT       |

### Locations — `/api/locations`

| Method | Path                                      | Auth  | Description                          |
|--------|-------------------------------------------|-------|--------------------------------------|
| GET    | `/api/locations?page=0&size=10&category=` | No    | Paginated list, optional category filter |
| GET    | `/api/locations/nearby?lat=&lng=&radius=` | No    | Locations within radius (km)         |
| GET    | `/api/locations/search?q=`                | No    | Full-text search                     |
| GET    | `/api/locations/{id}`                     | No    | Single location with reviews         |
| POST   | `/api/locations`                          | ADMIN | Create location                      |
| PUT    | `/api/locations/{id}`                     | ADMIN | Update location                      |
| DELETE | `/api/locations/{id}`                     | ADMIN | Delete location                      |

### Reviews — `/api`

| Method | Path                                     | Auth | Description              |
|--------|------------------------------------------|------|--------------------------|
| POST   | `/api/locations/{locationId}/reviews`    | Yes  | Add review (rating 1–5)  |
| GET    | `/api/locations/{locationId}/reviews`    | No   | Paginated reviews        |
| DELETE | `/api/reviews/{id}`                      | Yes  | Delete own review        |

### Favorites — `/api/favorites`

| Method | Path                        | Auth | Description              |
|--------|-----------------------------|------|--------------------------|
| POST   | `/api/favorites/{locationId}` | Yes | Save location            |
| DELETE | `/api/favorites/{locationId}` | Yes | Unsave location          |
| GET    | `/api/favorites`              | Yes | List saved locations     |

### Standard Response Format

**Success (paginated):**
```json
{
  "content": [...],
  "page": 0,
  "size": 10,
  "totalElements": 100,
  "totalPages": 10
}
```

**Error:**
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Location with id 42 not found",
  "timestamp": "2026-06-04T12:00:00Z"
}
```

---

## Running Tests

```bash
cd backend
mvn test
```

Tests cover `LocationService`, `AuthService`, and `LocationController` (MockMvc).

---

## Screenshots

> _Add screenshots here after first deployment._

| Home Page | Location Detail | Favorites |
|-----------|----------------|-----------|
| _TODO_    | _TODO_         | _TODO_    |

---

## Contributing

1. Fork the repo and create a feature branch.
2. Run `mvn test` before opening a PR.
3. Follow Java naming conventions: `camelCase` methods, `PascalCase` classes, `UPPER_SNAKE_CASE` constants.
