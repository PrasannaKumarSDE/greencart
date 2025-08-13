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

