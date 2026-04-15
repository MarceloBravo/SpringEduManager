# SpringEduManager

Sistema de gestión educativa desarrollado con Spring Boot para la administración de cursos, estudiantes, evaluaciones y usuarios.

## Características

- **Gestión de Cursos**: CRUD completo para cursos académicos
- **Gestión de Estudiantes**: Administración de estudiantes con asignación a cursos
- **Gestión de Evaluaciones**: Sistema de calificaciones con vista pivotizada
- **Gestión de Usuarios**: Sistema de autenticación con roles (ADMIN/USER)
- **Interfaz Web**: UI moderna con Thymeleaf, Bootstrap y TailwindCSS
- **API REST**: Endpoints para operaciones CRUD
- **Paginación y Búsqueda**: Búsqueda multi-campo con paginación
- **Seguridad**: Spring Security con autenticación por formulario

## Tecnologías Utilizadas

### Backend
- **Java 17+**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Spring Security**
- **MySQL/MariaDB**
- **Maven**

### Frontend
- **Thymeleaf**
- **Bootstrap 5**
- **TailwindCSS**
- **Material Symbols Icons**
- **JavaScript (ES6+)**

## Requisitos Previos

- Java 17 o superior
- Maven 3.6+
- MySQL 8.0+ o MariaDB 10.5+
- IDE recomendado: IntelliJ IDEA o Eclipse

## Instalación

### 1. Clonar el Repositorio

```bash
git clone [URL_DEL_REPOSITORIO]
cd SpringEduManager/web/web
```

### 2. Configurar la Base de Datos

Crear una base de datos en MySQL/MariaDB:

```sql
CREATE DATABASE springedu_manager CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'springedu'@'localhost' IDENTIFIED BY 'tu_password';
GRANT ALL PRIVILEGES ON springedu_manager.* TO 'springedu'@'localhost';
FLUSH PRIVILEGES;
```

### 3. Configurar Propiedades

Editar el archivo `src/main/resources/application.properties`:

```properties
# Configuración de Base de Datos
spring.datasource.url=jdbc:mysql://localhost:3306/springedu_manager
spring.datasource.username=springedu
spring.datasource.password=tu_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuración JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# Configuración de Thymeleaf
spring.thymeleaf.cache=false

# Configuración de seguridad
spring.security.user.name=admin
spring.security.user.password=admin123
```

### 4. Instalar Dependencias

```bash
mvn clean install
```

## Ejecución

### Método 1: Maven

```bash
mvn spring-boot:run
```

### Método 2: Ejecutable JAR

```bash
mvn clean package
java -jar target/SpringEduManager-0.0.1-SNAPSHOT.jar
```

### Método 3: IDE

Ejecutar la clase principal `SpringEduManagerApplication.java` desde tu IDE.

## Acceso a la Aplicación

Una vez iniciada la aplicación, acceder a:

- **URL Principal**: http://localhost:8080
- **Login por defecto**:
  - Usuario: `admin`
  - Contraseña: `admin123`

## Estructura del Proyecto

```
src/
|-- main/
|   |-- java/com/SpringEduManager/web/
|   |   |-- controllers/          # Controladores Web y REST
|   |   |-- dto/                 # Objetos de Transferencia de Datos
|   |   |-- entities/            # Entidades JPA
|   |   |-- enums/               # Enumeraciones
|   |   |-- repositories/        # Repositorios Spring Data
|   |   |-- services/            # Capa de Servicios
|   |   |-- config/              # Configuraciones
|   |   |-- SpringEduManagerApplication.java
|   |-- resources/
|   |   |-- static/              # Recursos estáticos (CSS, JS, imágenes)
|   |   |-- templates/           # Plantillas Thymeleaf
|   |   |   |-- fragments/       # Fragmentos reutilizables
|   |   |   |-- views/           # Vistas principales
|   |   |-- application.properties
|   |-- test/                    # Pruebas unitarias
```

## Funcionalidades Principales

### Gestión de Usuarios
- Registro de nuevos usuarios
- Asignación de roles (ADMIN/USER)
- Autenticación y autorización

### Gestión de Cursos
- Crear, editar, eliminar cursos
- Asignar estudiantes a cursos
- Búsqueda y paginación

### Gestión de Estudiantes
- CRUD completo de estudiantes
- Asignación a múltiples cursos
- Búsqueda por nombre, apellido, email

### Sistema de Evaluaciones
- Registro de calificaciones
- Vista pivotizada de notas por estudiante
- Gestión por curso y estudiante

## API REST

La aplicación expone endpoints REST bajo `/api/`:

- `/api/cursos` - Gestión de cursos
- `/api/estudiantes` - Gestión de estudiantes
- `/api/usuarios` - Gestión de usuarios
- `/api/evaluaciones` - Gestión de evaluaciones

Ejemplo de uso:

```bash
# Obtener todos los cursos
curl http://localhost:8080/api/cursos

# Crear nuevo estudiante
curl -X POST http://localhost:8080/api/estudiantes \
  -H "Content-Type: application/json" \
  -d '{"nombre":"Juan","apellido":"Pérez","email":"juan@email.com"}'
```


**Nota**: Esta aplicación fue desarrollada como proyecto educativo para demostrar el uso de Spring Boot y tecnologías relacionadas.
