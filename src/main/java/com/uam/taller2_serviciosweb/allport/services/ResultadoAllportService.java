package com.uam.taller2_serviciosweb.allport.services;

import com.uam.taller2_serviciosweb.allport.dto.ResultadoAllportDto;
import com.uam.taller2_serviciosweb.shared.exceptions.RecursoNoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ResultadoAllportService {

    private final Map<Long, ResultadoAllportDto> resultados = new ConcurrentHashMap<>();
    private final AtomicLong secuencia = new AtomicLong();

    public List<ResultadoAllportDto> listar() {
        return resultados.values().stream()
                .sorted(Comparator.comparing(ResultadoAllportDto::id))
                .toList();
    }

    public ResultadoAllportDto buscarPorId(Long id) {
        return obtenerExistente(id);
    }

    public ResultadoAllportDto registrar(ResultadoAllportDto datos) {
        long id = secuencia.incrementAndGet();
        ResultadoAllportDto resultado = datos.conId(id);
        resultados.put(id, resultado);
        return resultado;
    }

    public ResultadoAllportDto actualizar(Long id, ResultadoAllportDto datos) {
        obtenerExistente(id);
        ResultadoAllportDto actualizado = datos.conId(id);
        resultados.put(id, actualizado);
        return actualizado;
    }

    public ResultadoAllportDto eliminar(Long id) {
        ResultadoAllportDto eliminado = resultados.remove(id);
        if (eliminado == null) {
            throw noEncontrado(id);
        }
        return eliminado;
    }

    private ResultadoAllportDto obtenerExistente(Long id) {
        ResultadoAllportDto resultado = resultados.get(id);
        if (resultado == null) {
            throw noEncontrado(id);
        }
        return resultado;
    }

    private RecursoNoEncontradoException noEncontrado(Long id) {
        return new RecursoNoEncontradoException("No existe un resultado Allport con id " + id);
    }
}
