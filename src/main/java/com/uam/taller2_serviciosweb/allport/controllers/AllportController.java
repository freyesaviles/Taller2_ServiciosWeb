package com.uam.taller2_serviciosweb.allport.controllers;

import com.uam.taller2_serviciosweb.allport.dto.AllportDto;
import com.uam.taller2_serviciosweb.allport.services.AllportService;
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
@RequestMapping("/api/allport")
public class AllportController {

    private final AllportService service;

    public AllportController(AllportService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<AllportDto>> listar() {

        return ResponseEntity.ok(
                service.listar()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AllportDto> buscarPorId(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                service.buscarPorId(id)
        );
    }

    @PostMapping
    public ResponseEntity<AllportDto> registrar(
            @Valid @RequestBody AllportDto datos
    ) {

        AllportDto resultado = service.registrar(datos);

        return ResponseEntity
                .created(
                        URI.create(
                                "/api/allport/" + resultado.id()
                        )
                )
                .body(resultado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AllportDto> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody AllportDto datos
    ) {

        return ResponseEntity.ok(
                service.actualizar(id, datos)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AllportDto> eliminar(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                service.eliminar(id)
        );
    }
}