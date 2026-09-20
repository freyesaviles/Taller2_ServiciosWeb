package com.uam.taller2_serviciosweb.thurstone;

import com.uam.taller2_serviciosweb.shared.exceptions.RecursoNoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ResultadoThurstoneService {

    private final Map<Long, ResultadoThurstoneDto> repositorio = new HashMap<>();
    private final AtomicLong contadorId = new AtomicLong(1);

    public List<ResultadoThurstoneDto> obtenerTodos() {
        return new ArrayList<>(repositorio.values());
    }

    public ResultadoThurstoneDto obtenerPorId(Long id) {
        ResultadoThurstoneDto resultado = repositorio.get(id);
        if (resultado == null) {
            throw new RecursoNoEncontradoException("Resultado con ID " + id + " no encontrado.");
        }
        return resultado;
    }

    public ResultadoThurstoneDto guardar(ResultadoThurstoneDto dto) {
        Long nuevoId = contadorId.getAndIncrement();
        NivelThurstone nivelCalculado = calcularNivel(dto.puntuacion());

        ResultadoThurstoneDto nuevoResultado = new ResultadoThurstoneDto(
                nuevoId,
                dto.nombreParticipante(),
                dto.correo(),
                dto.edad(),
                dto.fechaAplicacion(),
                dto.puntuacion(),
                nivelCalculado,
                dto.completado() != null ? dto.completado() : true
        );

        repositorio.put(nuevoId, nuevoResultado);
        return nuevoResultado;
    }

    public ResultadoThurstoneDto actualizar(Long id, ResultadoThurstoneDto dto) {
        if (!repositorio.containsKey(id)) {
            throw new RecursoNoEncontradoException("Resultado con ID " + id + " no encontrado.");
        }

        NivelThurstone nivelCalculado = calcularNivel(dto.puntuacion());
        ResultadoThurstoneDto resultadoActualizado = new ResultadoThurstoneDto(
                id,
                dto.nombreParticipante(),
                dto.correo(),
                dto.edad(),
                dto.fechaAplicacion(),
                dto.puntuacion(),
                nivelCalculado,
                dto.completado()
        );

        repositorio.put(id, resultadoActualizado);
        return resultadoActualizado;
    }

    public void eliminar(Long id) {
        if (!repositorio.containsKey(id)) {
            throw new RecursoNoEncontradoException("Resultado con ID " + id + " no encontrado.");
        }
        repositorio.remove(id);
    }

    private NivelThurstone calcularNivel(Integer puntuacion) {
        if (puntuacion == null) return NivelThurstone.DEFICIENTE;
        if (puntuacion >= 80) return NivelThurstone.EXCELENTE;
        if (puntuacion >= 60) return NivelThurstone.SUPERIOR;
        if (puntuacion >= 40) return NivelThurstone.PROMEDIO;
        if (puntuacion >= 20) return NivelThurstone.INFERIOR;
        return NivelThurstone.DEFICIENTE;
    }
}