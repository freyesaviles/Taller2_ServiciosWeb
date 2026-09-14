package com.uam.taller2_serviciosweb.shared.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class ManejadorGlobalExcepciones {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRespuesta> manejarValidacion(MethodArgumentNotValidException excepcion) {
        Map<String, String> errores = new LinkedHashMap<>();
        for (FieldError error : excepcion.getBindingResult().getFieldErrors()) {
            errores.putIfAbsent(error.getField(), error.getDefaultMessage());
        }

        ErrorRespuesta respuesta = new ErrorRespuesta(
                HttpStatus.BAD_REQUEST.value(),
                "Los datos enviados no son válidos",
                errores
        );
        return ResponseEntity.badRequest().body(respuesta);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorRespuesta> manejarJsonInvalido() {
        ErrorRespuesta respuesta = new ErrorRespuesta(
                HttpStatus.BAD_REQUEST.value(),
                "El cuerpo de la solicitud no contiene un JSON válido",
                Map.of("solicitud", "Revise los tipos de datos y los valores permitidos")
        );
        return ResponseEntity.badRequest().body(respuesta);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorRespuesta> manejarNoEncontrado(RecursoNoEncontradoException excepcion) {
        ErrorRespuesta respuesta = new ErrorRespuesta(
                HttpStatus.NOT_FOUND.value(),
                excepcion.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
    }
}
