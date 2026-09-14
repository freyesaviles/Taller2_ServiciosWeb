package com.uam.taller2_serviciosweb.weill.services;

import com.uam.taller2_serviciosweb.shared.exceptions.RecursoNoEncontradoException;
import com.uam.taller2_serviciosweb.weill.dto.ResultadoWeillDto;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ResultadoWeillService {

    private final Map<Long, ResultadoWeillDto> resultados = new ConcurrentHashMap<>();
    private final AtomicLong secuencia = new AtomicLong();

    public List<ResultadoWeillDto> listar() {
        return resultados.values().stream()
                .sorted(Comparator.comparing(ResultadoWeillDto::id))
                .toList();
    }

    public ResultadoWeillDto buscarPorId(Long id) {
        return obtenerExistente(id);
    }

    public ResultadoWeillDto registrar(ResultadoWeillDto datos) {
        long id = secuencia.incrementAndGet();
        ResultadoWeillDto resultado = datos.conId(id);
        resultados.put(id, resultado);
        return resultado;
    }

    public ResultadoWeillDto actualizar(Long id, ResultadoWeillDto datos) {
        obtenerExistente(id);
        ResultadoWeillDto actualizado = datos.conId(id);
        resultados.put(id, actualizado);
        return actualizado;
    }

    public ResultadoWeillDto eliminar(Long id) {
        ResultadoWeillDto eliminado = resultados.remove(id);
        if (eliminado == null) {
            throw noEncontrado(id);
        }
        return eliminado;
    }

    private ResultadoWeillDto obtenerExistente(Long id) {
        ResultadoWeillDto resultado = resultados.get(id);
        if (resultado == null) {
            throw noEncontrado(id);
        }
        return resultado;
    }

    private RecursoNoEncontradoException noEncontrado(Long id) {
        return new RecursoNoEncontradoException("No existe un resultado Weill con id " + id);
    }
}
