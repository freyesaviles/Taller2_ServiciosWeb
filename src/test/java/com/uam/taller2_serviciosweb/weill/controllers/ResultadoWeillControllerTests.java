package com.uam.taller2_serviciosweb.weill.controllers;

import com.uam.taller2_serviciosweb.weill.dto.NivelWeill;
import com.uam.taller2_serviciosweb.weill.dto.ResultadoWeillDto;
import com.uam.taller2_serviciosweb.weill.services.ResultadoWeillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ResultadoWeillControllerTests {

    private static final String RUTA = "/api/resultados-weill";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ResultadoWeillService service;

    @Test
    void listaLosResultados() throws Exception {
        service.registrar(resultadoValido());

        mockMvc.perform(get(RUTA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void buscaUnResultadoPorId() throws Exception {
        ResultadoWeillDto guardado = service.registrar(resultadoValido());

        mockMvc.perform(get(RUTA + "/{id}", guardado.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(guardado.id()))
                .andExpect(jsonPath("$.nombreParticipante").value("Ana López"));
    }

    @Test
    void registraUnResultado() throws Exception {
        mockMvc.perform(post(RUTA)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonValido()))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.nivel").value("ALTO"));
    }

    @Test
    void actualizaUnResultado() throws Exception {
        ResultadoWeillDto guardado = service.registrar(resultadoValido());

        mockMvc.perform(put(RUTA + "/{id}", guardado.id())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonValido().replace("Ana López", "Carlos Ruiz")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(guardado.id()))
                .andExpect(jsonPath("$.nombreParticipante").value("Carlos Ruiz"));
    }

    @Test
    void eliminaUnResultado() throws Exception {
        ResultadoWeillDto guardado = service.registrar(resultadoValido());

        mockMvc.perform(delete(RUTA + "/{id}", guardado.id()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(guardado.id()));

        mockMvc.perform(get(RUTA + "/{id}", guardado.id()))
                .andExpect(status().isNotFound());
    }

    @Test
    void rechazaCamposVaciosYFueraDeRango() throws Exception {
        String jsonInvalido = jsonValido()
                .replace("Ana López", "")
                .replace("\"edad\": 24", "\"edad\": 3");

        mockMvc.perform(post(RUTA)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.estado").value(400))
                .andExpect(jsonPath("$.mensaje").value("Los datos enviados no son válidos"))
                .andExpect(jsonPath("$.errores.*", hasSize(2)));
    }

    @Test
    void rechazaUnaFechaFutura() throws Exception {
        String jsonInvalido = jsonValido().replace("2026-09-10", "2099-01-01");

        mockMvc.perform(post(RUTA)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errores.fechaAplicacion").exists());
    }

    @Test
    void responde404CuandoElIdNoExiste() throws Exception {
        mockMvc.perform(get(RUTA + "/999999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.estado").value(404))
                .andExpect(jsonPath("$.mensaje").value("No existe un resultado Weill con id 999999"));
    }

    private ResultadoWeillDto resultadoValido() {
        return new ResultadoWeillDto(null, "Ana López", "ana@example.com", 24,
                LocalDate.of(2026, 9, 10), 82, NivelWeill.ALTO, true);
    }

    private String jsonValido() {
        return """
                {
                  "nombreParticipante": "Ana López",
                  "correo": "ana@example.com",
                  "edad": 24,
                  "fechaAplicacion": "2026-09-10",
                  "puntuacion": 82,
                  "nivel": "ALTO",
                  "finalizado": true
                }
                """;
    }
}
