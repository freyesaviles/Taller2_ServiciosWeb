package com.uam.taller2_serviciosweb.thurstone;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resultados-thurstone")
public class ResultadoThurstoneController {

    private final ResultadoThurstoneService service;

    public ResultadoThurstoneController(ResultadoThurstoneService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ResultadoThurstoneDto>> listarTodos() {
        return ResponseEntity.ok(service.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoThurstoneDto> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ResultadoThurstoneDto> crear(@Valid @RequestBody ResultadoThurstoneDto dto) {
        ResultadoThurstoneDto creado = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultadoThurstoneDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ResultadoThurstoneDto dto) {
        return ResponseEntity.ok(service.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}