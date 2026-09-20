package com.uam.taller2_serviciosweb.allport.controllers;

import com.uam.taller2_serviciosweb.allport.dto.ResultadoAllportDto;
import com.uam.taller2_serviciosweb.allport.services.ResultadoAllportService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/resultados-allport")
public class ResultadoAllportController {

    private final ResultadoAllportService service;

    public ResultadoAllportController(ResultadoAllportService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ResultadoAllportDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoAllportDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ResultadoAllportDto> registrar(@Valid @RequestBody ResultadoAllportDto datos) {
        ResultadoAllportDto creado = service.registrar(datos);
        return ResponseEntity.created(URI.create("/api/resultados-allport/" + creado.id())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultadoAllportDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ResultadoAllportDto datos) {
        return ResponseEntity.ok(service.actualizar(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultadoAllportDto> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(service.eliminar(id));
    }
}
