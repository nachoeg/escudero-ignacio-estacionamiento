# Estacionamiento

Proyecto en Angular y Spring Boot para la gestión de un sistema de estacionamiento.

### Requisitos

- Java 21
- Maven 3.9.6
- Node.js 10.9.0
- PostgreSQL 16.8

### Instalación

1. Clonar el repositorio

```bash
git clone https://github.com/nachoeg/escudero-ignacio-estacionamiento
cd escudero-ignacio-estacionamiento

```

2. Instalar dependencias

```bash
cd frontend
npm install
cd ../backend
mvn install
```

3. Configurar la base de datos PostgreSQL
   - Instalar PostgreSQL
   - Crear una base de datos en PostgreSQL llamada `estacionamiento`.

```bash
sudo -u postgres psql
CREATE DATABASE estacionamiento;
```

### Ejecutar el backend

```bash
cd backend
mvn spring-boot:run
```

### Ejecutar el frontend

```bash
cd frontend
npm start
```

### Ver documentación backend

- Abrir el navegador y acceder a `http://localhost:8080/docs` para ver la documentación de la API REST generada por Swagger.

### Usuarios de prueba

| Telefono   | Contraseña | Email             |
| ---------- | ---------- | ----------------- |
| 1234567891 | usuario1   | usuario1@mail.com |
| 1234567892 | usuario2   | usuario2@mail.com |
| 1234567893 | usuario3   | usuario3@mail.com |
