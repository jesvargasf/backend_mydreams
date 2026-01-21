# My Dreams - Microservicio Backend

Microservicio Spring Boot para la gestión de productos de la pastelería My Dreams.

## Características

- ✅ CRUD completo de productos
- ✅ Autenticación JWT
- ✅ Base de datos H2 en memoria
- ✅ API RESTful
- ✅ Listado de productos público
- ✅ Operaciones de creación, actualización y eliminación protegidas con JWT
- ✅ Documentación completa con Swagger/OpenAPI

## Requisitos

- Java 17 o superior
- Maven 3.6 o superior

## Instalación y Ejecución

1. Navegar al directorio del proyecto:
```bash
cd backend
```

2. Compilar el proyecto:
```bash
mvn clean install
```

3. Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

La aplicación estará disponible en `http://localhost:8080`

## Documentación Swagger/OpenAPI

La API está completamente documentada con Swagger. Una vez que la aplicación esté ejecutándose, puedes acceder a:

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **API Docs (JSON)**: `http://localhost:8080/v3/api-docs`
- **API Docs (YAML)**: `http://localhost:8080/v3/api-docs.yaml`

### Características de la Documentación

- ✅ Documentación completa de todos los endpoints
- ✅ Esquemas de request/response
- ✅ Ejemplos de uso
- ✅ Autenticación JWT integrada (botón "Authorize")
- ✅ Pruebas interactivas desde el navegador
- ✅ Códigos de respuesta HTTP documentados

### Cómo usar Swagger UI

1. Accede a `http://localhost:8080/swagger-ui.html`
2. Para probar endpoints protegidos:
   - Primero, usa el endpoint `/api/auth/login` para obtener un token
   - Haz clic en el botón **"Authorize"** (🔒) en la parte superior
   - Ingresa el token en el formato: `Bearer {tu_token}`
   - Haz clic en **"Authorize"** y luego en **"Close"**
   - Ahora puedes probar los endpoints protegidos

## Base de Datos H2

La consola de H2 está disponible en: `http://localhost:8080/h2-console`

- **JDBC URL**: `jdbc:h2:mem:mydreamsdb`
- **Usuario**: `sa`
- **Contraseña**: (vacía)

## API Endpoints

### Autenticación

#### Login
```
POST /api/auth/login
Content-Type: application/json

{
  "username": "admin",
  "password": "admin123"
}
```

**Respuestas:**
- **200 OK**: Login exitoso
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "tipo": "Bearer"
}
```

- **400 Bad Request**: Error de validación
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Error de validación",
  "errors": {
    "username": "El username es requerido",
    "password": "La contraseña es requerida"
  },
  "timestamp": "2024-01-15T10:30:00",
  "path": "/api/auth/login"
}
```

- **401 Unauthorized**: Credenciales incorrectas
```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Usuario o contraseña incorrectos",
  "timestamp": "2024-01-15T10:30:00",
  "path": "/api/auth/login"
}
```

### Productos

#### Listar todos los productos (PÚBLICO)
```
GET /api/productos
```

**Respuestas:**
- **200 OK**: Lista de productos
```json
[
  {
    "id": 1,
    "nombre": "Kutchen de Manzana",
    "descripcion": "Delicioso kutchen casero...",
    "precio": 5500,
    "imagenUrl": "kutchenDeManzana.jpg",
    "categoria": "Kutchen",
    "activo": true
  }
]
```

#### Obtener producto por ID (PÚBLICO)
```
GET /api/productos/{id}
```

**Respuestas:**
- **200 OK**: Producto encontrado
- **404 Not Found**: Producto no encontrado
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Producto no encontrado con id: 999",
  "timestamp": "2024-01-15T10:30:00",
  "path": "/api/productos/999"
}
```

#### Crear producto (REQUIERE AUTENTICACIÓN)
```
POST /api/productos
Authorization: Bearer {token}
Content-Type: application/json

{
  "nombre": "Torta de Chocolate",
  "descripcion": "Deliciosa torta de chocolate",
  "precio": 15000,
  "imagenUrl": "tortaChocolate.jpg",
  "categoria": "Tortas"
}
```

**Respuestas:**
- **201 Created**: Producto creado exitosamente
- **400 Bad Request**: Error de validación
- **401 Unauthorized**: Token no proporcionado o inválido
- **403 Forbidden**: Sin permisos para crear productos
- **500 Internal Server Error**: Error del servidor

#### Actualizar producto (REQUIERE AUTENTICACIÓN)
```
PUT /api/productos/{id}
Authorization: Bearer {token}
Content-Type: application/json

{
  "nombre": "Torta de Chocolate Premium",
  "descripcion": "Deliciosa torta de chocolate premium",
  "precio": 18000,
  "imagenUrl": "tortaChocolate.jpg",
  "categoria": "Tortas"
}
```

**Respuestas:**
- **200 OK**: Producto actualizado exitosamente
- **400 Bad Request**: Error de validación
- **401 Unauthorized**: Token no proporcionado o inválido
- **403 Forbidden**: Sin permisos para actualizar productos
- **404 Not Found**: Producto no encontrado
- **500 Internal Server Error**: Error del servidor

#### Eliminar producto (REQUIERE AUTENTICACIÓN)
```
DELETE /api/productos/{id}
Authorization: Bearer {token}
```

**Respuestas:**
- **204 No Content**: Producto eliminado exitosamente
- **401 Unauthorized**: Token no proporcionado o inválido
- **403 Forbidden**: Sin permisos para eliminar productos
- **404 Not Found**: Producto no encontrado
- **500 Internal Server Error**: Error del servidor

## Códigos de Respuesta HTTP

El API utiliza los siguientes códigos de respuesta HTTP de manera consistente:

| Código | Descripción | Uso |
|--------|-------------|-----|
| **200 OK** | Operación exitosa | GET, PUT exitosos |
| **201 Created** | Recurso creado exitosamente | POST exitoso |
| **204 No Content** | Operación exitosa sin contenido | DELETE exitoso |
| **400 Bad Request** | Error de validación o solicitud incorrecta | Datos inválidos en el request |
| **401 Unauthorized** | No autenticado o credenciales inválidas | Token faltante o inválido, login incorrecto |
| **403 Forbidden** | Sin permisos para acceder al recurso | Token válido pero sin permisos |
| **404 Not Found** | Recurso no encontrado | ID de producto no existe |
| **500 Internal Server Error** | Error interno del servidor | Errores inesperados |

### Formato de Respuestas de Error

Todas las respuestas de error siguen el siguiente formato:

```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Mensaje descriptivo del error",
  "timestamp": "2024-01-15T10:30:00",
  "path": "/api/productos/999"
}
```

Para errores de validación (400), se incluye un objeto `errors` con los detalles:

```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Error de validación",
  "errors": {
    "nombre": "El nombre es requerido",
    "precio": "El precio debe ser positivo"
  },
  "timestamp": "2024-01-15T10:30:00",
  "path": "/api/productos"
}
```

## Usuarios por Defecto

El sistema incluye dos usuarios predefinidos:

1. **Admin**
   - Username: `admin`
   - Password: `admin123`
   - Rol: ADMIN

2. **Usuario**
   - Username: `user`
   - Password: `user123`
   - Rol: USER

## Productos por Defecto

El sistema carga automáticamente 17 productos de ejemplo al iniciar, incluyendo:
- Kutchen de Manzana
- Pie de Limón
- Torta Crema Piña
- Brownie Chocolate Nuez
- Cupcakes Variedades
- Y más...

## Estructura del Proyecto

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/mydreams/
│   │   │   ├── config/          # Configuraciones (Security, Data)
│   │   │   ├── controller/      # Controladores REST
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── entity/          # Entidades JPA
│   │   │   ├── exception/      # Excepciones personalizadas y manejador global
│   │   │   ├── repository/      # Repositorios JPA
│   │   │   ├── security/        # Filtros de seguridad
│   │   │   ├── service/         # Lógica de negocio
│   │   │   └── util/            # Utilidades (JWT)
│   │   └── resources/
│   │       └── application.properties
│   └── test/
└── pom.xml
```

## Tecnologías Utilizadas

- Spring Boot 3.3.0
- Spring Security
- Spring Data JPA
- H2 Database (desarrollo)
- PostgreSQL (producción)
- JWT (JSON Web Tokens)
- Springdoc OpenAPI (Swagger)
- Maven
- Lombok

## Despliegue en Render

### Requisitos para Despliegue

1. **Cuenta en Render**: Crea una cuenta en [render.com](https://render.com)
2. **GitHub**: Sube el código a un repositorio de GitHub
3. **Base de datos PostgreSQL**: Render proporciona una base de datos PostgreSQL gratuita

### Configuración del Despliegue

1. **Crear Web Service en Render**:
   - Conecta tu repositorio de GitHub
   - Selecciona el directorio `backend`
   - Render detectará automáticamente que es un proyecto Maven

2. **Configuración Simple**:
   - No se necesitan variables de entorno (todo está configurado en duro)
   - La aplicación usará base de datos H2 en memoria
   - JWT secret y otras configuraciones están hardcoded

3. **Build Command**:
   ```bash
   ./mvnw clean package -DskipTests
   ```

4. **Start Command**:
   ```bash
   java -jar target/backend-1.0.0.jar --spring.profiles.active=prod
   ```

### Archivos de Configuración para Render

- `application-prod.properties`: Configuración para producción con H2
- `render.yaml`: Configuración específica de Render (opcional)
- `Dockerfile`: Soporte para despliegue con Docker (opcional)

### Notas Importantes para Testing

- **Base de Datos**: H2 en memoria (los datos se pierden si el servicio se reinicia)
- **Sin Variables de Entorno**: Todo configurado en duro para facilitar testing
- **CORS**: Configurado para localhost y Render frontend
- **JWT**: Secret hardcoded para pruebas
- **Health Check**: `/actuator/health` para monitoreo de Render

### Flujo de Despliegue

1. **Push a GitHub**: Cada push a tu rama principal activará el despliegue automático
2. **Build**: Render compilará el proyecto con Maven
3. **Deploy**: La aplicación se iniciará con el perfil `prod` usando H2

### Verificación del Despliegue

Una vez desplegado, puedes verificar:

- **API Health**: `https://tu-app.onrender.com/actuator/health`
- **Swagger**: Deshabilitado en producción por seguridad
- **Logs**: Disponibles en el panel de Render

### Notas de Producción (Testing)

- La base de datos H2 está en memoria (datos se pierden al reiniciar)
- Swagger está deshabilitado por seguridad
- Los logs están configurados en nivel WARN para reducir ruido
- CORS está configurado para localhost y dominios de prueba
- El health check de Render usa `/actuator/health`
- **Todo está configurado en duro para facilitar testing**

## Notas

- El token JWT expira después de 24 horas (86400000 ms)
- La eliminación de productos es lógica (soft delete), se marca como inactivo
- El listado de productos solo muestra productos activos
- CORS está configurado para permitir peticiones desde `http://localhost:5173` y `http://localhost:3000`
- Todos los errores son manejados de forma centralizada con códigos HTTP consistentes
- El sistema incluye un manejador global de excepciones que retorna respuestas estructuradas