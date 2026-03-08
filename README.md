# ForoHub - Alura Challenge (Backend)

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

## 📌 Descripción del Proyecto
ForoHub es una API REST diseñada para replicar el funcionamiento de un foro de discusión. Permite a los usuarios gestionar tópicos de discusión siguiendo las mejores prácticas de desarrollo backend, incluyendo seguridad robusta y persistencia de datos.

## 🛠️ Funcionalidades
- **Autenticación Segura:** Implementación de login con Spring Security y tokens JWT.
- **CRUD de Tópicos:** Gestión completa (Crear, Listar, Detallar, Actualizar y Eliminar).
- **Validaciones de Datos:** Uso de Bean Validation para garantizar la integridad de la información.
- **Base de Datos:** Persistencia relacional con MySQL y Spring Data JPA.
- **Front End interactivo**

## 📁 Estructura del Proyecto
```text
forohub/
├── src/main/java/com/alura/forohub/
│   ├── controller/      # Endpoints (Autenticación, Tópicos, Usuarios)
│   ├── domain/          # Lógica de Negocio (Entidades, Repositorios, DTOs)
│   │   ├── topico/      # Gestión de Tópicos (Evita duplicados)
│   │   └── usuario/     # Gestión de Usuarios y Perfiles
│   └── infra/           # Seguridad (JWT, Filtros) y Manejo de Errores
├── src/main/resources/
│   ├── static/          # Frontend Interactivo (index, css, js)
│   └── application.properties
└── screenshots/         # Capturas de funcionamiento en Insomnia y Front End
```

## ⚙️ Configuración
- Clonar el repositorio.
- Crea una tabla en MySQL Workbench.
```sql
    # CREATE DATABASE_forohub_db;
```
- Configura las variables de entorno de desarrollo (Obligatorio)


| Variable      | Descripción                           |
|:--------------|:--------------------------------------| 
| `DB_USER`     | Tu usuario de MySQL                   |
| `DB_PASSWORD` | Tu contraseña de MySQL                | 
| `JWT_SECRET`  | Clave secreta para la firma de tokens |

- Ejecuta ``ForohubApplication``

## 📲 EndPoints (Insomnia) e interacción con el Front End

| Acción          | Ruta                   | Auth  |
|:----------------|:-----------------------|:------|
| Registrar       | `POST /registro`       | No    |
| Login           | `POST /login`          | No    |
| Crear           | `POST /topicos`        | Sí    |
| Consultar lista | `GET /topicos`         | Sí    |
| Consultar       | `GET /topicos/{id}`    | Sí    |
| Actualizar      | `PUT /topicos/{id}`    | Sí    |
| Eliminar        | `DELETE /topicos/{id}` | Sí    |

- Front End interactivo en: http://localhost:8080

### **📑 Stack Tecnológico**

| 🏷️ Categoría                   | 🚀 Tecnología                          |
|:--------------------------------|:---------------------------------------|
| **☕ Lenguaje de Programación**  | Java 17                                |
| **🍃 Framework Principal**      | Spring Boot 4.0.3                      |
| **📦 Gestión de Dependencias**  | Maven                                  |
| **🗄️ Base de Datos**           | MySQL 8.0                              |
| **💾 Persistencia (ORM)**       | Spring Data JPA / Hibernate            |
| **🔐 Seguridad**                | Spring Security & JWT (auth0 v4.4.0)   |
| **✅ Validación de Datos**       | Bean Validation (Hibernate Validator)  |
| **🌐 Frontend**                 | HTML5, CSS3, JavaScript (Fetch API)    |
| **🏗️ Utilidades**              | Lombok                                 |

---

### **🔧 Herramientas de Desarrollo**

| 🛠️ Herramienta        | 🎯 Uso                                   |
|:-----------------------|:-----------------------------------------|
| **💻 IntelliJ IDEA**   | IDE de desarrollo principal              |
| **🐬 MySQL Workbench** | Gestión y diseño de la base de datos     |
| **📡 Insomnia**        | Pruebas de Endpoints y validación de API |
| **🐙 Git**             | Control de versiones                     |

---
### 👤 Desarrollado por:
**Adrian Delgado** <br> *Participante del programa Oracle Next Education (ONE) en alianza con Alura Latam.*

[![GitHub](https://img.shields.io/badge/GitHub-100000?style=for-the-badge&logo=github&logoColor=white)](https://github.com/adriandlg-m)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-0077B5?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/TU_USUARIO_LINKEDIN)
