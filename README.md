# API de resultados psicométricos — Taller 2

API REST desarrollada con Spring Boot para administrar aplicaciones del test de
inteligencia Weill. El proyecto sirve también como base para incorporar, en módulos
independientes, los tests Allport y Thurstone.

## Estado de los módulos

| Test psicométrico | Módulo API | Pruebas automatizadas | Estado |
|---|---|---|---|
| Weill | `weill` | `ResultadoWeillControllerTests` | Implementado |
| Allport | `allport` | Se agregarán con el módulo | En espera |
| Thurstone | `thurstone` | Se agregarán con el módulo | En espera |

Cada test psicométrico tendrá sus propios DTO, servicio, controlador, endpoints y
pruebas automatizadas. Por ahora solamente Weill está implementado; Allport y
Thurstone quedan expresamente pendientes para que los integrantes responsables
los desarrollen sobre esta misma base.

## Entidad seleccionada

`ResultadoWeill` representa la aplicación del test a una persona. Contiene:

| Campo | Tipo | Validaciones principales |
|---|---|---|
| `id` | `Long` | Generado por el servicio |
| `nombreParticipante` | `String` | Obligatorio, entre 3 y 100 caracteres |
| `correo` | `String` | Obligatorio y con formato de correo |
| `edad` | `Integer` | Obligatoria, entre 6 y 120 |
| `fechaAplicacion` | `LocalDate` | Obligatoria, presente o pasada |
| `puntuacion` | `Integer` | Obligatoria, entre 0 y 100 |
| `nivel` | `NivelWeill` | Obligatorio: `BAJO`, `PROMEDIO` o `ALTO` |
| `finalizado` | `Boolean` | Obligatorio |

> Los rangos de puntuación y los niveles se modelaron con fines académicos. Si el
> instrumento utilizado por la asignatura maneja otra escala, basta con ajustar las
> validaciones y los valores del enum antes de entregar.

Los datos se almacenan en memoria y se pierden al reiniciar la aplicación. Esto
mantiene el alcance del taller centrado en REST, DTO y validación, sin introducir
una base de datos no solicitada.

## Requisitos y ejecución

- JDK 17 o posterior.
- No es necesario instalar Maven: el repositorio incluye Maven Wrapper.

```bash
./mvnw spring-boot:run
```

La API queda disponible en `http://localhost:8080`. Para ejecutar las pruebas
automatizadas:

```bash
./mvnw test
```

### Pruebas automatizadas actuales

`ResultadoWeillControllerTests` contiene las ocho pruebas actuales del módulo
Weill:

1. Listar todos los resultados correctamente.
2. Buscar un resultado existente por identificador.
3. Registrar un resultado válido.
4. Actualizar un resultado existente.
5. Eliminar un resultado existente.
6. Rechazar un nombre vacío y una edad fuera del rango permitido.
7. Rechazar una fecha de aplicación futura.
8. Responder `404 Not Found` ante un identificador inexistente.

Cuando se implementen Allport y Thurstone, cada módulo deberá incorporar una clase
de pruebas equivalente para verificar sus cinco endpoints, sus validaciones y el
manejo de identificadores inexistentes. Esas pruebas todavía están en espera.

## Endpoints

| Método | Ruta | Resultado exitoso | Errores posibles |
|---|---|---|---|
| GET | `/api/resultados-weill` | `200` y arreglo JSON | — |
| GET | `/api/resultados-weill/{id}` | `200` y resultado | `404` |
| POST | `/api/resultados-weill` | `201`, cabecera `Location` y resultado | `400` |
| PUT | `/api/resultados-weill/{id}` | `200` y resultado actualizado | `400`, `404` |
| DELETE | `/api/resultados-weill/{id}` | `200` y resultado eliminado | `404` |

El archivo [requests.http](requests.http) contiene las ocho pruebas requeridas y
puede ejecutarse directamente desde el cliente HTTP de IntelliJ IDEA, en orden de
arriba hacia abajo.

## Pruebas manuales en Postman

Antes de probar, inicie la aplicación con `./mvnw spring-boot:run`. En Postman cree
una colección y defina la variable `baseUrl` con el valor
`http://localhost:8080`. Ejecute las solicitudes en el orden mostrado porque el
registro creado en la primera prueba se utiliza en las pruebas siguientes.

### 1. Registrar un resultado válido

- Método: `POST`
- URL: `{{baseUrl}}/api/resultados-weill`
- Body: seleccione **raw** y **JSON**.
- Resultado esperado: `201 Created`, un objeto JSON con `id` y una cabecera
  `Location`.

```json
{
  "nombreParticipante": "Ana López",
  "correo": "ana@example.com",
  "edad": 24,
  "fechaAplicacion": "2026-09-10",
  "puntuacion": 82,
  "nivel": "ALTO",
  "finalizado": true
}
```

Copie el `id` de la respuesta y cree en la colección la variable `resultadoId`
con ese valor.

### 2. Listar todos los resultados

- Método: `GET`
- URL: `{{baseUrl}}/api/resultados-weill`
- Resultado esperado: `200 OK` y un arreglo JSON que contiene el registro creado.

### 3. Buscar el resultado por identificador

- Método: `GET`
- URL: `{{baseUrl}}/api/resultados-weill/{{resultadoId}}`
- Resultado esperado: `200 OK` y el objeto correspondiente.

### 4. Actualizar el resultado

- Método: `PUT`
- URL: `{{baseUrl}}/api/resultados-weill/{{resultadoId}}`
- Body: seleccione **raw** y **JSON**.
- Resultado esperado: `200 OK`, el mismo `id` y los datos actualizados.

```json
{
  "nombreParticipante": "Ana López Martínez",
  "correo": "ana.martinez@example.com",
  "edad": 25,
  "fechaAplicacion": "2026-09-11",
  "puntuacion": 88,
  "nivel": "ALTO",
  "finalizado": true
}
```

### 5. Validar nombre vacío y edad fuera de rango

- Método: `POST`
- URL: `{{baseUrl}}/api/resultados-weill`
- Body: seleccione **raw** y **JSON**.
- Resultado esperado: `400 Bad Request`, con detalles para
  `nombreParticipante` y `edad` dentro de `errores`.

```json
{
  "nombreParticipante": "",
  "correo": "persona@example.com",
  "edad": 3,
  "fechaAplicacion": "2026-09-10",
  "puntuacion": 70,
  "nivel": "PROMEDIO",
  "finalizado": true
}
```

### 6. Validar correo y fecha futura

- Método: `POST`
- URL: `{{baseUrl}}/api/resultados-weill`
- Body: seleccione **raw** y **JSON**.
- Resultado esperado: `400 Bad Request`, con detalles para `correo` y
  `fechaAplicacion` dentro de `errores`.

```json
{
  "nombreParticipante": "Luis Pérez",
  "correo": "correo-invalido",
  "edad": 30,
  "fechaAplicacion": "2099-01-01",
  "puntuacion": 60,
  "nivel": "PROMEDIO",
  "finalizado": false
}
```

### 7. Buscar un identificador inexistente

- Método: `GET`
- URL: `{{baseUrl}}/api/resultados-weill/999999`
- Resultado esperado: `404 Not Found` con `estado` y `mensaje` en JSON.

```json
{
  "estado": 404,
  "mensaje": "No existe un resultado Weill con id 999999"
}
```

### 8. Eliminar el resultado

- Método: `DELETE`
- URL: `{{baseUrl}}/api/resultados-weill/{{resultadoId}}`
- Resultado esperado: `200 OK` y el objeto que fue eliminado.

Al finalizar estas pruebas se cubren los cinco endpoints exitosos, dos casos con
datos inválidos y un caso con identificador inexistente solicitados en el taller.
Para las evidencias, cada captura debe mostrar el método, URL, cuerpo enviado,
código HTTP y respuesta JSON cuando corresponda.

## Organización y recorrido de una solicitud

```text
Solicitud HTTP
  -> weill/controllers/ResultadoWeillController
  -> validación automática de ResultadoWeillDto
  -> weill/services/ResultadoWeillService
  -> ResponseEntity + serialización JSON
```

Si la validación falla o el recurso no existe, la excepción pasa a
`shared/exceptions/ManejadorGlobalExcepciones`, que construye la respuesta JSON con
el código HTTP correspondiente.

## Cómo agregar Allport o Thurstone

Cada integrante debe crear un paquete de dominio hermano de `weill`, por ejemplo:

```text
allport/
  controllers/
  dto/
  services/
thurstone/
  controllers/
  dto/
  services/
shared/
  exceptions/   (reutilizado por todos)
```

El nuevo módulo debe tener su propio DTO, enum si lo requiere, servicio y
controlador con una ruta que no colisione, por ejemplo `/api/resultados-allport`.
No se debe copiar el manejador global: las excepciones de todos los módulos pueden
usar `RecursoNoEncontradoException` y serán transformadas automáticamente.

## Evidencias

Después de iniciar la aplicación, ejecute las ocho solicitudes de
`requests.http` y guarde una captura de cada respuesta en `evidencias/` siguiendo
los nombres indicados en esa carpeta. Cada imagen debe mostrar método, URL, cuerpo
enviado cuando corresponda, estado HTTP y respuesta JSON.
