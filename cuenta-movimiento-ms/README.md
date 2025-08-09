# 📌 NTT Banca - Microservicios Cliente/Persona y Cuenta/Movimiento

Este proyecto implementa dos microservicios en **Java 17 + Spring Boot 3** siguiendo principios de **arquitectura hexagonal**.  
La comunicación es **asíncrona** y cada servicio maneja su propia base de datos en **MySQL**.

---

## 🖥️ 1. Resumen del Proyecto

- **Lenguaje:** Java 17  
- **Framework:** Spring Boot 3.5.4  
- **Arquitectura:** Hexagonal (Ports & Adapters)  
- **Persistencia:** Spring Data JPA + MySQL 8  
- **Documentación API:** Swagger/OpenAPI  
- **Pruebas:** JUnit 5 + Mockito  
- **Contenedores:** Docker Compose para base de datos  

---

## 🏗️ 2. Arquitectura General

```mermaid
flowchart LR
    A[Cliente-Persona-MS] -- Eventos → MQ
    MQ[Message Broker] -- Eventos → B[Cuenta-Movimiento-MS]
    A <--> DB1[(MySQL clientes_db)]
    B <--> DB2[(MySQL cuentas_db)]
