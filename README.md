```markdown
# 🎬 Movie Database REST API

A robust back-end RESTful API for managing a movie database. Built with Java and Spring Boot, this project features automated web scraping, role-based security, and database schema versioning.

## 🚀 Key Features

* **Automated Web Scraping:** Programmatically extracts movie data (titles, release years, ratings) from external sources using Jsoup.
* **Role-Based Security:** Secured endpoints using Spring Security (Basic Authentication). Read access is public, while data modification and scraping operations are restricted to administrators.
* **Database Migrations:** Utilizes Flyway for reliable database schema initialization and version control (configured via manual bootstrap to ensure proper execution order).
* **Pagination & Search:** Efficiently handles large datasets with Spring Data JPA pagination and provides endpoints for filtering by title or minimum rating.
* **Interactive Documentation:** Automatically generates a Swagger UI (OpenAPI) dashboard for seamless API testing and exploration.

## 🛠 Tech Stack

* **Language:** Java 26
* **Framework:** Spring Boot 4.1.0
* **Database:** H2 Database (File-based)
* **ORM:** Hibernate / Spring Data JPA
* **Migrations:** Flyway
* **Web Scraping:** Jsoup
* **Security:** Spring Security
* **API Documentation:** Springdoc OpenAPI (Swagger)

## ⚙️ Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/iliyamedvediev/movies-api.git
   ```
2. Update Maven dependencies and build the project.
3. Run the MoviesApplication.java main class. Flyway will automatically create the database and movie table on startup.

API Endpoints
Once the application is running, access the interactive Swagger UI documentation at:
http://localhost:8080/swagger-ui.html

Public Endpoints (No Auth Required):

GET /movies — Retrieve a paginated list of movies.

GET /movies/search?title={text} — Search movies by title.

GET /movies/min-rating?rating={number} — Filter movies by minimum rating.

Admin Endpoints (Basic Auth Required):

POST /movies/scrape — Trigger the web scraper to populate the database.

DELETE /movies/{id} — Delete a specific movie by ID.
(Default Admin Credentials - Username: admin, Password: supersecret)