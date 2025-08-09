# 📌 NTT Banca — Microservicios Cliente/Persona y Cuenta/Movimiento

**Arquitectura:** Hexagonal (Ports & Adapters)  
**Lenguaje:** Java 17 + Spring Boot 3.5.4  
**Base de datos:** MySQL 8  
**Despliegue:** Docker + Docker Compose  
**Documentación API:** Swagger/OpenAPI

---

## 📂 Estructura del repositorio

```
.
├── cliente-persona-ms/       # Gestión de clientes y personas
├── cuenta-movimiento-ms/     # Gestión de cuentas y movimientos
├── docker-compose.yml        # Orquestación de microservicios y MySQL
├── .env                      # Variables de entorno
└── README.md
```

---

## ⚙️ Puertos

| Servicio             | Puerto |
| -------------------- | ------ |
| Cliente-Persona-MS   | 8081   |
| Cuenta-Movimiento-MS | 8082   |
| MySQL clientes_db    | 3306   |
| MySQL cuentas_db     | 3307   |

---

## ▶️ Ejecución con Docker

1. **Clonar el repositorio**

```bash
git clone https://github.com/usuario/ntt-banca-2023.git
cd ntt-banca-2023
```

2. **Crear archivo `.env`**

```env
MYSQL_ROOT_PASSWORD=admin
```

3. **Levantar todos los servicios**

```bash
docker compose up -d --build
```

4. **Detener y eliminar contenedores y volúmenes**

```bash
docker compose down -v
```

---

## 📖 Endpoints principales

### Cliente-Persona-MS

| Método | Endpoint       | Descripción        |
| ------ | -------------- | ------------------ |
| POST   | /clientes      | Crear cliente      |
| GET    | /clientes      | Listar clientes    |
| GET    | /clientes/{id} | Obtener por ID     |
| PUT    | /clientes/{id} | Actualizar cliente |
| DELETE | /clientes/{id} | Soft delete        |

### Cuenta-Movimiento-MS

| Método | Endpoint                 | Descripción            |
| ------ | ------------------------ | ---------------------- |
| POST   | /cuentas                 | Crear cuenta           |
| GET    | /cuentas/{id}            | Obtener por ID         |
| POST   | /movimientos             | Registrar movimiento   |
| GET    | /movimientos/cuenta/{id} | Movimientos por cuenta |
| GET    | /reportes/{idCliente}    | Reporte movimientos    |

---

## 🔎 Swagger

- Cliente-Persona-MS → [http://localhost:8081/swagger-ui/index.html](http://localhost:8081/swagger-ui/index.html)
- Cuenta-Movimiento-MS → [http://localhost:8082/swagger-ui/index.html](http://localhost:8082/swagger-ui/index.html)

---

**Autor:** Carlos Díaz  
📄 _Prueba Técnica NTT DATA_
