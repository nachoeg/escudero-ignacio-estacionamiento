# estacionamiento

Proyecto en Angular y Spring Boot para la gestión de un sistema de estacionamiento.

### Instalación

1. Clonar el repositorio

```bash
git clone <repositorio>
cd <repositorio>
```

2. Instalar dependencias

```bash
cd frontend
npm install
cd ../backend
mvn install
```

3. Configurar la base de datos

   - Crear una base de datos en MySQL llamada `estacionamiento`.
   - Importar el archivo `estacionamiento.sql` en la base de datos.
   - Configurar el archivo `application.properties` en el backend con los datos de conexión a la base de datos.
   - Ejemplo de configuración:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/estacionamiento
spring.datasource.username=root
spring.datasource.password=123456
spring.jpa.hibernate.ddl-auto=update
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

### Usuarios de prueba

| Telefono   | Contraseña | Email             |
| ---------- | ---------- | ----------------- |
| 1234567891 | usuario1   | usuario1@mail.com |
| 1234567892 | usuario2   | usuario2@mail.com |
| 1234567893 | usuario3   | usuario3@mail.com |
