package com.uam.taller2_serviciosweb.allport.services;

import com.uam.taller2_serviciosweb.allport.dto.AllportDto;
import com.uam.taller2_serviciosweb.shared.exceptions.RecursoNoEncontradoException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AllportService {

    private final Map<Long, AllportDto> resultados =
            new ConcurrentHashMap<>();

    private final AtomicLong secuencia = new AtomicLong();

    public List<AllportDto> listar() {

        return resultados.values()
                .stream()
                .sorted(Comparator.comparing(AllportDto::id))
                .toList();
    }

    public AllportDto buscarPorId(Long id) {

        return obtenerExistente(id);
    }

    public AllportDto registrar(AllportDto datos) {

        Long id = secuencia.incrementAndGet();

        AllportDto nuevoResultado = datos.conId(id);

        resultados.put(id, nuevoResultado);

        return nuevoResultado;
    }

    public AllportDto actualizar(Long id, AllportDto datos) {

        obtenerExistente(id);

        AllportDto resultadoActualizado = datos.conId(id);

        resultados.put(id, resultadoActualizado);

        return resultadoActualizado;
    }

    public AllportDto eliminar(Long id) {

        AllportDto resultado = resultados.remove(id);

        if (resultado == null) {
            throw new RecursoNoEncontradoException(
                    "No existe un resultado Allport con id " + id
            );
        }

        return resultado;
    }

    private AllportDto obtenerExistente(Long id) {

        AllportDto resultado = resultados.get(id);

        if (resultado == null) {
            throw new RecursoNoEncontradoException(
                    "No existe un resultado Allport con id " + id
            );
        }

        return resultado;
    }
}