# API de Tests Psicométricos — Taller 2

API REST desarrollada con Spring Boot para administrar resultados de los tests
psicométricos del proyecto de asignatura.

## Estado de los módulos

| Test | Estado       | Ruta base |
|---|--------------|--|
| Weill | Implementado | `/api/resultados-weill` |
| Allport | Implementado |`/api/resultados-allport` |
| Thurstone | Pendiente    | Por definir |

Cada test tendrá su propio DTO, servicio, controlador y colección de Postman. El
manejo de errores en `shared/exceptions` es común para todos los módulos.

## Requisitos y ejecución

- JDK 17 o posterior.
- Maven Wrapper incluido en el proyecto.

```bash
./mvnw spring-boot:run
```

La API estará disponible en `http://localhost:8080`.

## Test de Weill

La entidad `ResultadoWeill` contiene: identificador, nombre del participante,
correo, edad, fecha de aplicación, puntuación, nivel y estado de finalización.
Los registros se almacenan temporalmente en memoria.

### Endpoints

| Método | Ruta | Respuesta esperada |
|---|---|---|
| GET | `/api/resultados-weill` | `200 OK` y arreglo JSON |
| GET | `/api/resultados-weill/{id}` | `200 OK` o `404 Not Found` |
| POST | `/api/resultados-weill` | `201 Created` o `400 Bad Request` |
| PUT | `/api/resultados-weill/{id}` | `200 OK`, `400` o `404` |
| DELETE | `/api/resultados-weill/{id}` | `200 OK` o `404 Not Found` |

### Pruebas en Postman

Importe la colección
[`postman/Taller2-Weill.postman_collection.json`](postman/Taller2-Weill.postman_collection.json)
y ejecute las solicitudes en orden:

1. Registrar un resultado válido.
2. Listar todos los resultados.
3. Buscar un resultado por identificador.
4. Actualizar el resultado.
5. Enviar un nombre vacío y una edad inválida.
6. Enviar un correo inválido y una fecha futura.
7. Buscar un identificador inexistente.
8. Eliminar el resultado.

La primera solicitud guarda automáticamente el identificador en `resultadoId`.
Las ocho pruebas cubren los cinco endpoints exitosos, dos casos de validación y un
caso `404`, como solicita el taller.

## Test de Allport (valores de Allport-Vernon-Lindzey)

La entidad `ResultadoAllport` contiene: identificador, nombre del participante,
correo, edad, fecha de aplicación, puntuación de cada uno de los seis valores
(teórico, económico, estético, social, político y religioso), el valor
dominante y el estado de finalización. Los registros se almacenan
temporalmente en memoria.

### Endpoints

| Método | Ruta | Respuesta esperada |
|---|---|---|
| GET | `/api/resultados-allport` | `200 OK` y arreglo JSON |
| GET | `/api/resultados-allport/{id}` | `200 OK` o `404 Not Found` |
| POST | `/api/resultados-allport` | `201 Created` o `400 Bad Request` |
| PUT | `/api/resultados-allport/{id}` | `200 OK`, `400` o `404` |
| DELETE | `/api/resultados-allport/{id}` | `200 OK` o `404 Not Found` |

### Pruebas en Postman

1. Registrar un resultado válido.
2. Listar todos los resultados.
3. Buscar un resultado por identificador.
4. Actualizar el resultado.
5. Enviar un nombre vacío y una edad inválida.
6. Enviar un correo inválido y una fecha futura.
7. Buscar un identificador inexistente.
8. Eliminar el resultado.

La primera solicitud guarda automáticamente el identificador en `resultadoId`.
Las ocho pruebas cubren los cinco endpoints exitosos, dos casos de validación y un
caso `404`, como solicita el taller.

## Evidencias

Guarde en `evidencias/` una captura de cada prueba de Postman. Cada captura debe
mostrar el método, la URL, los datos enviados, el código HTTP y la respuesta JSON.
