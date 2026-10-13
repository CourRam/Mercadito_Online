# Mercadito Online

Aplicación web de compra y venta de productos (tipo marketplace) desarrollada con **Spring Boot** y **Oracle Database**. Los usuarios pueden publicar productos, organizarlos por categorías, agregarlos a un carrito y completar la compra, que queda registrada en un historial de ventas.

Proyecto académico de la Universidad Autónoma de Chihuahua (UACH).

## Funcionalidades

- Registro de usuarios (nombre, correo único, teléfono y dirección).
- Publicación de productos con descripción, precio, stock, categoría e imagen.
- Catálogo organizado por categorías.
- Carrito de compras con detalle por producto (cantidad y subtotal).
- Historial de ventas por usuario con fecha y total.

## Tecnologías

| Capa | Herramientas |
|---|---|
| Backend | Java 25, Spring Boot 3.5.7, Spring Data JPA, Bean Validation |
| Frontend | Thymeleaf |
| Base de datos | Oracle Database (driver `ojdbc11`) |
| Otras | Lombok, Apache Camel 4.15, Maven |

## Modelo de datos

El script [`Crear Tablas.sql`](Crear%20Tablas.sql) crea seis tablas:

```
Usuarios ──< Productos >── Categorias
   │
   ├──< Carrito ──< Detalle_Carrito >── Productos
   │       │
   └──< HistorialVentas >──┘
```

| Tabla | Descripción |
|---|---|
| `Usuarios` | Datos de cada usuario; el correo es único. |
| `Categorias` | Categorías de productos. |
| `Productos` | Producto, precio, stock actual e inicial, vendedor y categoría. |
| `Carrito` | Carrito por usuario con estado (`EN_PROCESO` por defecto). |
| `Detalle_Carrito` | Productos de cada carrito con cantidad y subtotal. |
| `HistorialVentas` | Ventas realizadas: carrito, usuario, fecha y total. |

## Cómo ejecutarlo

### Requisitos

- JDK 25
- Oracle Database accesible (local o remota)
- Maven (o el wrapper incluido `mvnw`)

### Pasos

1. Clona el repositorio:
   ```bash
   git clone https://github.com/CourRam/Mercadito_Online.git
   cd Mercadito_Online
   ```
2. Ejecuta `Crear Tablas.sql` en tu esquema de Oracle.
3. Configura la conexión a tu base de datos (URL, usuario y contraseña) en `src/main/resources/application.properties`. <!-- [COMPLETAR: confirma la ruta y el nombre de las propiedades que usas] -->
4. Inicia la aplicación:
   ```bash
   ./mvnw spring-boot:run        # Linux / macOS
   mvnw.cmd spring-boot:run      # Windows
   ```
5. Abre `http://localhost:8080` en el navegador.

## Estructura del proyecto

```
Mercadito_Online/
├── src/                  # Código fuente (Java, plantillas Thymeleaf, recursos)
├── uploads/productos/    # Imágenes de productos subidas por los usuarios
├── Crear Tablas.sql      # Script de creación de la base de datos
├── Manual Técnico.pdf    # Documentación técnica
├── Manual de Usuario.pdf # Guía de uso
└── pom.xml               # Dependencias y configuración de Maven
```

## Documentación

- [Manual Técnico](Manual%20T%C3%A9cnico.pdf)
- [Manual de Usuario](Manual%20de%20Usuario.pdf)

## Autor

**Joseph Daniel Ramírez Torres** · [GitHub](https://github.com/CourRam)
