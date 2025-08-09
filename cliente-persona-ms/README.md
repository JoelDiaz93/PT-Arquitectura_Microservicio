# Cliente Persona MS

Microservicio (Java 17, Spring Boot 3.2.4) con arquitectura en capas, IDs Long autoincrement, Swagger, H2 para local y MySQL en Docker.

## Ejecutar local
```bash
./mvnw spring-boot:run
```
Swagger UI: http://localhost:8080/swagger-ui/index.html

## Perfiles
- Local (H2): `application.yml`
- Docker (MySQL): `application-docker.yml` con `SPRING_PROFILES_ACTIVE=docker`

## Docker
```bash
docker compose up --build
```

## Endpoints
- `GET /clientes`
- `GET /clientes/{id}`
- `POST /clientes`
- `PUT /clientes/{id}`
- `DELETE /clientes/{id}`