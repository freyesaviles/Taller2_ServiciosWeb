# API de Tests Psicométricos — Taller 2

API REST desarrollada con Spring Boot para administrar resultados de los tests
psicométricos Weill, Allport y Thurstone.

## Módulos

| Test | Entidad principal | Ruta base | Estado |
|---|---|---|---|
| Weill | `ResultadoWeill` | `/api/resultados-weill` | Implementado |
| Allport | `ResultadoAllport` | `/api/resultados-allport` | Implementado |
| Thurstone | `ResultadoThurstone` | `/api/resultados-thurstone` | Implementado |

Cada módulo contiene su DTO, servicio y controlador. Los tres reutilizan el manejo
global de validaciones y recursos inexistentes ubicado en `shared/exceptions`. Los
registros se almacenan temporalmente en memoria y se pierden al reiniciar la
aplicación.

## Requisitos y ejecución

- JDK 17 o posterior.
- Maven Wrapper incluido en el proyecto.

```bash
./mvnw spring-boot:run
```

La API estará disponible en `http://localhost:8080`.

## Endpoints de Weill

| Método | Ruta | Respuesta exitosa |
|---|---|---|
| GET | `/api/resultados-weill` | `200 OK` y arreglo JSON |
| GET | `/api/resultados-weill/{id}` | `200 OK` y resultado |
| POST | `/api/resultados-weill` | `201 Created` y resultado creado |
| PUT | `/api/resultados-weill/{id}` | `200 OK` y resultado actualizado |
| DELETE | `/api/resultados-weill/{id}` | `200 OK` y resultado eliminado |

## Endpoints de Allport

| Método | Ruta | Respuesta exitosa |
|---|---|---|
| GET | `/api/resultados-allport` | `200 OK` y arreglo JSON |
| GET | `/api/resultados-allport/{id}` | `200 OK` y resultado |
| POST | `/api/resultados-allport` | `201 Created` y resultado creado |
| PUT | `/api/resultados-allport/{id}` | `200 OK` y resultado actualizado |
| DELETE | `/api/resultados-allport/{id}` | `200 OK` y resultado eliminado |

## Endpoints de Thurstone

| Método | Ruta | Respuesta exitosa |
|---|---|---|
| GET | `/api/resultados-thurstone` | `200 OK` y arreglo JSON |
| GET | `/api/resultados-thurstone/{id}` | `200 OK` y resultado |
| POST | `/api/resultados-thurstone` | `201 Created` y resultado creado |
| PUT | `/api/resultados-thurstone/{id}` | `200 OK` y resultado actualizado |
| DELETE | `/api/resultados-thurstone/{id}` | `204 No Content` |

En los tres módulos, los datos inválidos producen `400 Bad Request` con el detalle
de los campos y los identificadores inexistentes producen `404 Not Found`.

## Pruebas

Los endpoints pueden probarse en Postman usando las rutas anteriores. Para cada
módulo se recomienda comprobar:

1. Registro exitoso con `POST`.
2. Listado con `GET`.
3. Búsqueda por identificador con `GET`.
4. Actualización con `PUT`.
5. Eliminación con `DELETE`.
6. Dos solicitudes con datos inválidos.
7. Una solicitud con un identificador inexistente.

El repositorio conserva la colección de referencia de Weill en `postman/` y sus
capturas en `evidencias/`.
