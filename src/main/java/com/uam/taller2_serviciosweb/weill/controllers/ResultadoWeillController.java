package com.uam.taller2_serviciosweb.weill.controllers;

import com.uam.taller2_serviciosweb.weill.dto.ResultadoWeillDto;
import com.uam.taller2_serviciosweb.weill.services.ResultadoWeillService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/resultados-weill")
public class ResultadoWeillController {

    private final ResultadoWeillService service;

    public ResultadoWeillController(ResultadoWeillService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ResultadoWeillDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResultadoWeillDto> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<ResultadoWeillDto> registrar(@Valid @RequestBody ResultadoWeillDto datos) {
        ResultadoWeillDto creado = service.registrar(datos);
        return ResponseEntity.created(URI.create("/api/resultados-weill/" + creado.id())).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResultadoWeillDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody ResultadoWeillDto datos) {
        return ResponseEntity.ok(service.actualizar(id, datos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResultadoWeillDto> eliminar(@PathVariable Long id) {
        return ResponseEntity.ok(service.eliminar(id));
    }
}
