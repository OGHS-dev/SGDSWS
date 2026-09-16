# SGDSWS
Sistema de Gestión de Desarrollo de Software con Seguridad

## SGDSWS 2.0

The legacy Thymeleaf application remains at the repository root. The upgraded
applications are organized as:

- `backend/`: Java 21 and Spring Boot 4.1.1 REST API.
- `frontend/`: Angular 22.1.x client with Bootstrap 5.3.8.

The backend reads database credentials and JWT secrets from environment variables
(`SPRING_DATASOURCE_PASSWORD`, `JWT_SECRET_KEY`, and related settings). The
frontend requires Node.js 22.22.3 or newer.
