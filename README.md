# API de resultados psicométricos — Taller 2

API REST desarrollada con Spring Boot para administrar aplicaciones del test de
inteligencia Weill. El proyecto sirve también como base para incorporar, en módulos
independientes, los tests Allport y Thurstone.

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
