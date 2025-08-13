# GreenCart Frontend

## Overview
The GreenCart frontend is a **single-page HTML/JavaScript UI** for a logistics management system.  
It provides:
- **Login** with JWT authentication.
- **Dashboard** (KPI cards, pie chart for deliveries, bar chart for cost breakdown).
- **Simulation** runner with live result display.
- **Management views** for Drivers, Routes, and Orders, with CRUD operations.

The frontend is built using **Bootstrap 5**, **Chart.js**, and vanilla JavaScript, and is served as a static resource from the Spring Boot backend.

---

## Tech Stack
- HTML5, CSS3, JavaScript (Vanilla JS)
- Bootstrap 5.3
- Chart.js 4.x
- Google Fonts (Poppins)
- Responsive design

---

## Setup Instructions
Since the frontend is embedded inside the backend project:

1. Ensure the backend is running (see backend README for details).
2. Open your browser at:http://localhost:8001


3. Log in using valid manager credentials (seeded in the backend database).
4. Use the navigation bar to switch between **Dashboard**, **Simulation**, and **Management**.

---

## Environment Variables
The frontend automatically uses the relative URL of the backend (`/auth/login`, `/api/**`).  
If you deploy frontend and backend separately, update the following in `index.html`:

const API_BASE = "https://your-backend-url";

## Features
- **Login/Logout** with JWT
- **Dashboard KPIs** from last simulation
- **Charts** for delivery performance and costs
- **Simulation runner** to generate new results
- **Management CRUD** for:
  - Drivers
  - Routes
  - Orders
  - 
Backend README.md
text
# GreenCart Backend

## Overview
The GreenCart backend is a **Spring Boot 3.5** REST API that powers the logistics management dashboard.  
It provides:
- JWT-based Authentication & Authorization
- Simulation engine for deliveries
- CRUD endpoints for Drivers, Routes, and Orders
- KPI & cost metrics storage in SimulationResult

---

## Tech Stack
- Java 17
- Spring Boot 3.5.4
- Spring Security with JWT
- Spring Data JPA (Hibernate)
- MySQL database
- Maven build tool
- JJWT (JWT handling)

---

## Prerequisites
- JDK 17+
- Maven 3.8+
- MySQL 8.x
- Git

---

## Setup Steps (Local)
1. **Clone the repository**
git clone https://github.com/yourusername/greencart.git
cd greencart/backend

text
2. **Configure Database**
- Create MySQL DB:
  ```
  CREATE DATABASE greencart;
  ```
- Update DB credentials in `src/main/resources/application.properties`:
  ```
  spring.datasource.url=jdbc:mysql://localhost:3306/greencart
  spring.datasource.username=your_db_user
  spring.datasource.password=your_db_password
  ```
3. **Set JWT Secrets**
In `application.properties`:
jwt.secret=your_base64_encoded_secret
jwt.expirationMs=86400000

text
4. **Seed Initial Data**
Insert at least one manager account (example for BCrypt-hashed password `password123`):
INSERT INTO user (username, password_hash, role)
VALUES ('manager1', '$2a$10$0yJAh.TszgdBp9XyYhQmjeyhxv/tLUkzsiPl9dC3POr6xqM731/om', 'ROLE_MANAGER');

Load CSV data for `drivers`, `routes`, `orders` if not auto-loaded.

5. **Run Application**
mvn spring-boot:run

text
Backend runs on [http://localhost:8001](http://localhost:8000)

---

## Environment Variables
| Variable           | Description                          |
|--------------------|--------------------------------------|
| `DB_URL`           | JDBC URL of the MySQL database       |
| `DB_USERNAME`      | MySQL username                       |
| `DB_PASSWORD`      | MySQL password                       |
| `JWT_SECRET`       | Base64-encoded signing key for JWT   |
| `JWT_EXPIRATION_MS`| Token expiration in milliseconds     |

---

## API Documentation

### Authentication
**POST** `/auth/login`  
Request:
{ "username": "manager1", "password": "password123" }

text
Response:
{ "token": "eyJhbGciOi..." }

text
localhost:8000/api/simulate
### Simulation
**POST** `/api/simulate`  
Runs new simulation with posted config.  
**GET** `/api/simulation/history`  
Returns all past runs.

localhost:8000/api/drivers/andall
### Management
- **Drivers** `/api/drivers`
- **Routes** `/api/routes`
- **Orders** `/api/orders`

Each supports:  
`GET` all, `GET /{id}`, `POST` create, `PUT /{id}` update, `DELETE /{id}` delete.

